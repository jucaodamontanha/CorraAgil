package br.com.pipocaagil.corraagil.controller;

import br.com.pipocaagil.corraagil.dto.CorridaRequestDTO;
import br.com.pipocaagil.corraagil.dto.CorridaResponseDTO;
import br.com.pipocaagil.corraagil.service.CorridaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações de corrida.
 */
@RestController
@RequestMapping("/corrida")
public class CorridaController {

    private final CorridaService corridaService;

    public CorridaController(CorridaService corridaService) {
        this.corridaService = corridaService;
    }

    @GetMapping("/cadastro/{id}")
    public ResponseEntity<List<CorridaResponseDTO>> listarCorridasPorCadastro(@PathVariable Long id) {
        List<CorridaResponseDTO> corridas = corridaService.listarCorridasPorCadastro(id);
        return ResponseEntity.ok(corridas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorridaResponseDTO> getCorridaById(@PathVariable Long id) {
        CorridaResponseDTO corrida = corridaService.buscarPorId(id);
        return ResponseEntity.ok(corrida);
    }

    @PostMapping("/cadastro/{id}")
    public ResponseEntity<CorridaResponseDTO> createCorrida(@PathVariable Long id, @Valid @RequestBody CorridaRequestDTO dto) {
        CorridaResponseDTO novaCorrida = corridaService.salvar(dto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCorrida);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCorrida(@PathVariable Long id) {
        corridaService.deletar(id);
    }
}