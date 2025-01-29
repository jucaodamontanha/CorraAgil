package br.com.pipocaagil.corraagil.Cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serviço para envio de emails de confirmação.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envia um email de confirmação.
     *
     * @param to destinatário do email
     * @param subject assunto do email
     * @param text texto do email
     */
    public void sendConfirmationEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}