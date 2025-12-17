package br.com.pipocaagil.corraagil.service;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.repository.CadastroRepository;
import br.com.pipocaagil.corraagil.repository.ResetTokenRepository;
import br.com.pipocaagil.corraagil.model.ResetToken; // Pacote a ser movido
import br.com.pipocaagil.corraagil.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Serviço para gerenciar operações de reset de senha.
 */
@Service
public class ResetService {

    private final CadastroRepository cadastroRepository;
    private final ResetTokenRepository resetTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependência via construtor (abordagem preferida)
    public ResetService(CadastroRepository cadastroRepository, ResetTokenRepository resetTokenRepository,
                        EmailService emailService, PasswordEncoder passwordEncoder) {
        this.cadastroRepository = cadastroRepository;
        this.resetTokenRepository = resetTokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    private String gerarToken() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public void gerarTokenParaEmail(String email) {
        CadastroModel cadastroModel = cadastroRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email));

        // Deleta tokens antigos do mesmo usuário para evitar duplicação
        resetTokenRepository.findByCadastroModel(cadastroModel)
                .ifPresent(resetTokenRepository::delete);

        String newToken = gerarToken();
        ResetToken resetToken = new ResetToken(newToken, cadastroModel);
        resetTokenRepository.save(resetToken);

        // Envia o e-mail com o token (agora responsabilidade do EmailService)
        // O corpo do e-mail deve ser criado em uma camada adequada, mas por simplicidade, passamos o token aqui.
        String htmlContent = "Seu código de redefinição de senha é: " + newToken;
        emailService.sendConfirmationEmail(email, "Código de Redefinição de Senha", htmlContent);
    }

    public boolean isTokenValido(String token) {
        Optional<ResetToken> resetTokenOpt = resetTokenRepository.findByToken(token);
        return resetTokenOpt.isPresent() && resetTokenOpt.get().isTokenValido();
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        ResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido ou não encontrado"));

        if (!resetToken.isTokenValido()) {
            resetTokenRepository.delete(resetToken);
            throw new ResourceNotFoundException("Token expirado");
        }

        CadastroModel user = resetToken.getCadastroModel();
        user.setSenha(passwordEncoder.encode(novaSenha)); // Criptografa a nova senha
        cadastroRepository.save(user);
        resetTokenRepository.delete(resetToken); // Remove o token após o uso
    }
}