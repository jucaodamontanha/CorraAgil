package br.com.pipocaagil.corraagil.corrida;

import br.com.pipocaagil.corraagil.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Serviço para gerenciar operações de corrida.
 */
@Service
public class CorridaService {
    @Autowired
    private CorridaRepository corridaRepository;

    /**
     * Inicia uma nova corrida.
     *
     * @return CorridaModel com os dados da corrida iniciada
     */
        public CorridaModel iniciarCorrida() {
            CorridaModel corridaModel = new CorridaModel();
            corridaModel.setInicio(LocalDateTime.now());
            corridaModel.setPausada(false);
            return corridaRepository.save(corridaModel);
        }

        /**
         * Pausa uma corrida existente.
         *
         * @param id ID da corrida a ser pausada
         * @return CorridaModel com os dados da corrida pausada
         */
        public CorridaModel pausarCorrida(Long id) {
            CorridaModel corridaModel = corridaRepository.findById(id).orElseThrow();
            corridaModel.setPausada(true);
            return corridaRepository.save(corridaModel);
        }

        /**
         * Continua uma corrida pausada.
         *
         * @param id ID da corrida a ser continuada
         * @return CorridaModel com os dados da corrida continuada
         */
        public CorridaModel continuarCorrida(Long id) {
            CorridaModel corridaModel = corridaRepository.findById(id).orElseThrow();
            corridaModel.setPausada(false);
            return corridaRepository.save(corridaModel);
        }

        /**
         * Finaliza uma corrida existente.
         *
         * @param id ID da corrida a ser finalizada
         * @return CorridaModel com os dados da corrida finalizada
         */
        public CorridaModel finalizarCorrida(Long id) {
            CorridaModel corridaModel = corridaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Corrida não encontrada com ID: " + id));
            corridaModel.setFim(LocalDateTime.now());
            return corridaRepository.save(corridaModel);
        }
}