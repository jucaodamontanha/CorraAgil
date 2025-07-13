package br.com.pipocaagil.corraagil.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Serviço para envio de emails de confirmação com suporte a HTML.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            ClassPathResource image = new ClassPathResource("imagem.png");
            helper.addInline("logoCorraAgil", image);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Falha ao enviar e-mail de confirmação: " + e.getMessage());
            //opcional: log.warn("Erro ao enviar email", e);
        }
    }
}
