package br.com.pipocaagil.corraagil.controller;

import br.com.pipocaagil.corraagil.dto.EmailRequestDTO;
import br.com.pipocaagil.corraagil.dto.PasswordResetRequestDTO;
import br.com.pipocaagil.corraagil.dto.TokenRequestDTO;
import br.com.pipocaagil.corraagil.exception.ResourceNotFoundException;
import br.com.pipocaagil.corraagil.service.ResetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gerenciar operações de reset de senha.
 */
@RestController
@RequestMapping("/")
public class ResetController {

    private final ResetService resetService;

    // Injeção de dependência via construtor
    public ResetController(ResetService resetService) {
        this.resetService = resetService;
    }

    @PostMapping("/resetSenha")
    public ResponseEntity<String> gerarToken(@Valid @RequestBody EmailRequestDTO requestDTO) {
        resetService.gerarTokenParaEmail(requestDTO.getEmail());
        return ResponseEntity.ok("Token de reset de senha enviado para o e-mail");
    }

    @PostMapping("/verificaToken")
    public ResponseEntity<String> validarToken(@Valid @RequestBody TokenRequestDTO requestDTO) {
        boolean isTokenValid = resetService.isTokenValido(requestDTO.getToken());
        if (isTokenValid) {
            return ResponseEntity.ok("Token válido");
        }
        throw new ResourceNotFoundException("Token inválido ou expirado");
    }

    @PostMapping("/saveSenha")
    public ResponseEntity<String> redefinirSenha(@Valid @RequestBody PasswordResetRequestDTO requestDTO) {
        resetService.redefinirSenha(requestDTO.getToken(), requestDTO.getNovaSenha());
        return ResponseEntity.ok("Senha alterada com sucesso");
    }

}