package br.com.pipocaagil.corraagil.service;

import br.com.pipocaagil.corraagil.dto.CorridaRequestDTO;
import br.com.pipocaagil.corraagil.dto.CorridaResponseDTO;
import br.com.pipocaagil.corraagil.exception.ResourceNotFoundException;
import br.com.pipocaagil.corraagil.mapper.CorridaMapper;
import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.model.CorridaModel;
import br.com.pipocaagil.corraagil.repository.CadastroRepository;
import br.com.pipocaagil.corraagil.repository.CorridaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorridaService {

    private final CorridaRepository corridaRepository;
    private final CadastroRepository cadastroRepository;
    private final CorridaMapper corridaMapper;

    public CorridaService(CorridaRepository corridaRepository, CadastroRepository cadastroRepository, CorridaMapper corridaMapper) {
        this.corridaRepository = corridaRepository;
        this.cadastroRepository = cadastroRepository;
        this.corridaMapper = corridaMapper;
    }

    public List<CorridaResponseDTO> listarCorridasPorCadastro(Long cadastroId) {
        // Encontra o cadastro ou lança uma exceção
        CadastroModel cadastro = cadastroRepository.findById(cadastroId)
                .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado com ID: " + cadastroId));

        return corridaRepository.findByCadastro(cadastro).stream()
                .map(corridaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CorridaResponseDTO buscarPorId(Long id) {
        CorridaModel corrida = corridaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Corrida não encontrada com ID: " + id));
        return corridaMapper.toResponseDTO(corrida);
    }

    public CorridaResponseDTO salvar(CorridaRequestDTO dto, Long cadastroId) {
        // Encontra o cadastro ou lança uma exceção
        CadastroModel cadastro = cadastroRepository.findById(cadastroId)
                .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado com ID: " + cadastroId));

        CorridaModel model = corridaMapper.toModel(dto);
        model.setCadastro(cadastro); // Associa a corrida ao cadastro

        CorridaModel salvo = corridaRepository.save(model);
        return corridaMapper.toResponseDTO(salvo);
    }

    public void deletar(Long id) {
        if (!corridaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Corrida não encontrada para deletar com ID: " + id);
        }
        corridaRepository.deleteById(id);
    }
}