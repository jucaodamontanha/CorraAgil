package br.com.pipocaagil.corraagil.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            try {
                // Adiciona a imagem de fundo 'imagem.png'
                ClassPathResource backgroundImage = new ClassPathResource("imagem.png");
                helper.addInline("imagemCorraAgil", backgroundImage);

                // Adiciona o logo 'logo.png'
                ClassPathResource logoImage = new ClassPathResource("logo.png");
                helper.addInline("logoCorraAgil", logoImage);

            } catch (Exception e) {
                logger.error("Falha ao adicionar recursos de imagem no e-mail", e);
            }

            mailSender.send(message);
            logger.info("E-mail com o assunto '{}' enviado com sucesso para '{}'.", subject, to);
        } catch (Exception e) {
            logger.error("Falha ao enviar e-mail com o assunto '{}' para '{}'.", subject, to, e);
        }
    }
}