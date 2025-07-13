package br.com.pipocaagil.corraagil.controller;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.repository.CadastroRepository;
import br.com.pipocaagil.corraagil.service.ResetService;
import br.com.pipocaagil.corraagil.Reset.ResetToken;
import br.com.pipocaagil.corraagil.repository.ResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * Controlador REST para gerenciar operações de reset de senha.
 */
@RestController
@RequestMapping("/")
public class ResetController {
    @Autowired
    private ResetService resetService;
    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private ResetTokenRepository resetTokenRepository;

    /**
     * Gera um token de 4 dígitos.
     *
     * @return token gerado
     */
    private String gerarToken() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Gera um token de 4 dígitos
    }

    /**
     * Envia um token de reset de senha para o email do usuário.
     *
     * @param request mapa contendo o email do usuário
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/resetSenha")
    public ResponseEntity resetSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        CadastroModel cadastroModel = cadastroRepository.findByEmail(email);

        if (cadastroModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        // Verifica se já existe um token associado ao usuário
        ResetToken existingToken = resetService.findTokenByCadastro(cadastroModel);

        if (existingToken != null) {
            // Se o token existir e estiver expirado, deleta do banco
            if (!existingToken.isTokenValido()) {
                resetService.deletarToken(existingToken);
            }
        }

        // Gera um novo token
        String newToken = gerarToken(); // Gera um token de 4 dígitos
        resetService.createResetTokenForCadastro(cadastroModel, newToken);
        resetService.ResetTokenEmail(email, newToken);

        return ResponseEntity.ok("Token de reset de senha enviado para o e-mail");
    }

    /**
     * Verifica a validade de um token de reset de senha.
     *
     * @param request mapa contendo o token
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/verificaToken")
    public ResponseEntity<String> verificaToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        ResetToken resetToken = resetTokenRepository.findByToken(token);

        if (resetToken == null || !resetToken.isTokenValido()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        // Se o token for válido, retorna o ID do usuário ou outra informação necessária
        return ResponseEntity.ok("Token válido");
    }

    /**
     * Salva a nova senha do usuário.
     *
     * @param request mapa contendo o token e a nova senha
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/saveSenha")
    public ResponseEntity<String> saveSenha(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String senha = request.get("senha");

        // Busca o token no banco de dados
        ResetToken resetToken = resetTokenRepository.findByToken(token);

        // Se o token não existir, retorna uma mensagem de erro
        if (resetToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou não encontrado");
        }
        // Recupera o usuário associado ao token
        CadastroModel user = resetToken.getCadastroModel();
        user.setSenha(senha);
        cadastroRepository.save(user); // Salva a nova senha
        resetTokenRepository.delete(resetToken); // Remove o token após o uso
        return ResponseEntity.ok("Senha alterada com sucesso");
    }
}