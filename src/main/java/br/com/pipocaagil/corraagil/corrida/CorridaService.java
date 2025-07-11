package br.com.pipocaagil.corraagil.corrida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorridaService {
    @Autowired
    private CorridaRepository corridaRepository;

    public List<CorridaModel> getAllCorridas() {
        return corridaRepository.findAll();
    }

    public CorridaModel getCorridaById(Long id) {
        return corridaRepository.findById(id).orElse(null);
    }

    public CorridaModel saveCorrida(CorridaModel corrida) {
        return corridaRepository.save(corrida);
    }

    public void deleteCorrida(Long id) {
        corridaRepository.deleteById(id);
    }
}