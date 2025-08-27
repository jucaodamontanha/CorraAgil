package br.com.pipocaagil.corraagil.service;

import br.com.pipocaagil.corraagil.dto.CadastroRequestDTO;
import br.com.pipocaagil.corraagil.dto.CadastroResponseDTO;
import br.com.pipocaagil.corraagil.exception.ResourceNotFoundException; // <-- Importação correta
import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.repository.CadastroRepository;
import br.com.pipocaagil.corraagil.mapper.CadastroMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações de cadastro.
 */
@Service
public class CadastroService {

    private final CadastroRepository cadastroRepository;
    private final CadastroMapper cadastroMapper;
    private final PasswordEncoder passwordEncoder;

    public CadastroService(CadastroRepository cadastroRepository, CadastroMapper cadastroMapper, PasswordEncoder passwordEncoder) {
        this.cadastroRepository = cadastroRepository;
        this.cadastroMapper = cadastroMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CadastroResponseDTO> listarTodos() {
        return cadastroRepository.findAll()
                .stream()
                .map(cadastroMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CadastroResponseDTO buscarPorId(Long id) {
        CadastroModel cadastro = cadastroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado com ID: " + id)); // <-- Troquei a exceção
        return cadastroMapper.toResponseDTO(cadastro);
    }

    public CadastroResponseDTO salvar(CadastroRequestDTO dto) {
        CadastroModel model = cadastroMapper.toModel(dto);
        model.setSenha(passwordEncoder.encode(model.getSenha()));
        CadastroModel salvo = cadastroRepository.save(model);
        return cadastroMapper.toResponseDTO(salvo);
    }

    public CadastroResponseDTO atualizar(Long id, CadastroRequestDTO dto) {
        CadastroModel cadastroExistente = cadastroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado!")); // <-- Troquei a exceção

        cadastroExistente.setNomeCompleto(dto.getNomeCompleto());
        cadastroExistente.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            cadastroExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        CadastroModel atualizado = cadastroRepository.save(cadastroExistente);
        return cadastroMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!cadastroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cadastro não encontrado para deletar com ID: " + id); // <-- Troquei a exceção
        }
        cadastroRepository.deleteById(id);
    }

    // O método autenticar foi removido

    public void atualizarSenha(Long id, String novaSenha) { // <-- Removi o 'throws CadastroNotFoundException'
        CadastroModel cadastro = cadastroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado")); // <-- Troquei a exceção
        cadastro.setSenha(passwordEncoder.encode(novaSenha));
        cadastroRepository.save(cadastro);
    }

    // O método buscarPorEmail foi removido
}