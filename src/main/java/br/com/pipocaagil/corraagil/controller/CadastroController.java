package br.com.pipocaagil.corraagil.controller;

import br.com.pipocaagil.corraagil.dto.CadastroRequestDTO;
import br.com.pipocaagil.corraagil.dto.CadastroResponseDTO;
import br.com.pipocaagil.corraagil.service.CadastroService;
import br.com.pipocaagil.corraagil.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações de cadastro.
 */
@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    private final CadastroService cadastroService;
    private final EmailService emailService;

    // Injeção de dependência via construtor
    public CadastroController(CadastroService cadastroService, EmailService emailService) {
        this.cadastroService = cadastroService;
        this.emailService = emailService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<CadastroResponseDTO>> getAllCadastro() {
        List<CadastroResponseDTO> lista = cadastroService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CadastroResponseDTO> buscarPorId(@PathVariable Long id) {
        CadastroResponseDTO dto = cadastroService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CadastroResponseDTO> createCadastro(@Valid @RequestBody CadastroRequestDTO dto) {
        CadastroResponseDTO saved = cadastroService.salvar(dto);

        String htmlContent = """
        <div style="font-family: 'Poppins', sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden;">
            <div style="background-image: url('cid:imagemCorraAgil'); background-size: cover; background-position: center; height: 250px; text-align: right; padding: 15px;">
                <h1 style="color: white; margin: 0; padding-right: 15px;">CorraÁGIL</h1>
            </div>
            <div style="padding: 20px; text-align: center;">
                <p style="font-size: 16px; color: #555;">Olá,</p>
                <p style="font-size: 16px; color: #555;">Você efetuou o cadastro do seu e-mail em nosso app, estamos fazendo a verificação e validação</p>
                <a href="#" style="display: inline-block; padding: 12px 24px; margin-top: 20px; background-color: #0d47a1; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;">
                    Clique aqui e confirme o seu e-mail
                </a>
                <p style="font-size: 14px; color: #888; margin-top: 40px;">Equipe, <strong>CorraÁGIL</strong>.</p>
            </div>
        </div>
        """;

        emailService.sendConfirmationEmail(dto.getEmail(), "Confirmação de Cadastro", htmlContent);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CadastroResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CadastroRequestDTO dto) {
        // O serviço irá lançar uma ResourceNotFoundException se o ID não existir.
        // Essa exceção será capturada pelo GlobalExceptionHandler.
        CadastroResponseDTO atualizado = cadastroService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // O serviço irá verificar a existência e lançar uma ResourceNotFoundException se o ID não existir.
        // Essa exceção será capturada pelo GlobalExceptionHandler.
        cadastroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}