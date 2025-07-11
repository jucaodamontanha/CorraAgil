package br.com.pipocaagil.corraagil.Reset;

import br.com.pipocaagil.corraagil.Cadastro.CadastroModel;
import br.com.pipocaagil.corraagil.Cadastro.CadastroRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
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
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Reset de Senha");

            // HTML estilizado com token
            String htmlContent = """
    <div style="font-family: Arial, sans-serif; padding: 20px; max-width: 600px; margin: auto; border: 1px solid #ccc;">
        <div style="background-color: #0f2439; padding: 30px; border-radius: 15px; color: white; text-align: center;">
            <img src='cid:logoCorraAgil' alt='CorraÁGIL' style='max-width: 150px; display: block; margin: auto;' />
            <div style="margin-top: 30px;">
                <img src='cid:cadeado' alt='Ícone Cadeado' style='width: 60px; height: 60px;' />
            </div>
            <h2 style="margin-top: 20px;">Seu link de verificação da CorraÁGIL é:</h2>
            <p style="color: #00BFFF; font-weight: bold;">
    """ + token + """
            </p>
        </div>
        <div style="margin-top: 30px; text-align: center; font-size: 15px; color: #000;">
            <p>Clique neste link para acessar sua conta da CorraÁGIL. Por motivos de segurança, não use este link fora da CorraÁGIL.</p>
            <p><strong>Nunca divulgue este link.</strong></p>
            <p>Este link será válido até <strong>2h</strong>, após esse prazo será necessário solicitar outro.</p>
            <p>Não solicitou este link? Faça login no seu perfil CorraÁGIL e atualize sua senha.</p>
        </div>
    </div>
    """;


            helper.setText(htmlContent, true);

            // Embutir as imagens
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/logo.png")); // substitua pela imagem do logotipo
            FileSystemResource cadeado = new FileSystemResource(new File("src/main/resources/cadeado.png")); // substitua pela imagem do cadeado
            helper.addInline("logoCorraAgil", logo);
            helper.addInline("cadeado", cadeado);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail de reset de senha", e);
        }
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