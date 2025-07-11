package br.com.pipocaagil.corraagil.Cadastro;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Serviço para envio de emails de confirmação com suporte a HTML.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envia um email de confirmação com corpo HTML.
     *
     * @param to          destinatário do email
     * @param subject     assunto do email
     * @param htmlContent conteúdo do email em HTML
     */
    public void sendConfirmationEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            // Usa o HTML com uma referência cid
            helper.setText(htmlContent, true);

            // Adiciona imagem inline
            FileSystemResource image = new FileSystemResource(new File("src/main/resources/imagem.png"));
            helper.addInline("logoCorraAgil", image);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de confirmação", e);
        }
    }

}