package br.com.pipocaagil.corraagil.corrida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * Controlador REST para gerenciar operações de corrida.
 */
@RestController
@RequestMapping("/corrida")
public class CorridaController {
    @Autowired
    private CorridaService corridaService;

    /**
     * Inicia uma nova corrida.
     *
     * @return CorridaModel com os dados da corrida iniciada
     */
    @PostMapping("/iniciar")
    public CorridaModel iniciarCorrida() {
        return corridaService.iniciarCorrida();
    }

    /**
     * Pausa uma corrida existente.
     *
     * @param id ID da corrida a ser pausada
     * @return CorridaModel com os dados da corrida pausada
     */
    @PostMapping("/pausar/{id}")
    public CorridaModel pararCorrida(@PathVariable Long id) {
        return corridaService.pausarCorrida(id);
    }

    /**
     * Continua uma corrida pausada.
     *
     * @param id ID da corrida a ser continuada
     * @return CorridaModel com os dados da corrida continuada
     */
    @PostMapping("/continuar/{id}")
    public CorridaModel continuarCorrida(@PathVariable Long id) {
        return corridaService.continuarCorrida(id);
    }

    /**
     * Finaliza uma corrida existente.
     *
     * @param id ID da corrida a ser finalizada
     * @return CorridaModel com os dados da corrida finalizada
     */
    @PostMapping("/finalizar/{id}")
    public ResponseEntity<String> finalizarCorrida(@PathVariable Long id) {
        CorridaModel corridaModel = corridaService.finalizarCorrida(id);
        Duration duracao = corridaModel.getDuracao();
        return ResponseEntity.ok("Duração da corrida: " + duracao.toMinutes() + " minutos");
    }
}