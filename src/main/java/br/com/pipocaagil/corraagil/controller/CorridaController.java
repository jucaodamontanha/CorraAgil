package br.com.pipocaagil.corraagil.controller;

import br.com.pipocaagil.corraagil.model.CorridaModel;
import br.com.pipocaagil.corraagil.service.CorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações de corrida.
 */
@RestController
@RequestMapping("/corrida")
public class CorridaController {
    @Autowired
    private CorridaService corridaService;

    @GetMapping
    public List<CorridaModel> getAllCorridas() {
        return corridaService.getAllCorridas();
    }

    @GetMapping("/{id}")
    public CorridaModel getCorridaById(@PathVariable Long id) {
        return corridaService.getCorridaById(id);
    }

    @PostMapping
    public CorridaModel createCorrida(@RequestBody CorridaModel corrida) {
        return corridaService.saveCorrida(corrida);
    }

    @DeleteMapping("/{id}")
    public void deleteCorrida(@PathVariable Long id) {
        corridaService.deleteCorrida(id);
    }
}
