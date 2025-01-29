package br.com.pipocaagil.corraagil.Reset;

import br.com.pipocaagil.corraagil.Cadastro.CadastroModel;
import br.com.pipocaagil.corraagil.Cadastro.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Serviço para gerenciar operações de reset de senha.
 */
@Service
public class ResetService {
    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private ResetTokenRepository resetTokenRepository;
    @Autowired
    private JavaMailSender mailSender;

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
     * Cria um token de reset para um cadastro.
     *
     * @param cadastroModel cadastro para o qual o token será criado
     * @param token token gerado
     */
    public void createResetTokenForCadastro(CadastroModel cadastroModel, String token) {
        ResetToken myToken = new ResetToken(token, cadastroModel);
        resetTokenRepository.save(myToken);
    }

    /**
     * Cria um token de reset para um cadastro.
     *
     * @param cadastroModel cadastro para o qual o token será criado
     */
    public void createResetTokenForCadastro(CadastroModel cadastroModel) {
        String token = gerarToken();
        ResetToken myToken = new ResetToken(token, cadastroModel);
        resetTokenRepository.save(myToken);
    }

    /**
     * Envia um email com o token de reset de senha.
     *
     * @param email email do destinatário
     * @param token token de reset de senha
     */
    public void ResetTokenEmail(String email, String token) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject("Reset de Senha");
        emailMessage.setText("Seu token de reset de senha é: " + token +
                "\n\nEste token é válido por 20 minutos.");
        mailSender.send(emailMessage);
    }

    /**
     * Busca um token de reset pelo cadastro.
     *
     * @param cadastroModel cadastro associado ao token
     * @return ResetToken encontrado
     */
    public ResetToken findTokenByCadastro(CadastroModel cadastroModel) {
        return resetTokenRepository.findByCadastroModel(cadastroModel);
    }

    /**
     * Deleta um token de reset.
     *
     * @param token token a ser deletado
     */
    public void deletarToken(ResetToken token) {
        resetTokenRepository.delete(token);
    }
}