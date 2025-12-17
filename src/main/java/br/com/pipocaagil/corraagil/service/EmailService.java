package br.com.pipocaagil.corraagil.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final SendGrid sendGridClient;

    @Value("${SENDGRID_FROM_EMAIL}")
    private String fromEmail;

    // Injeta o cliente SendGrid configurado
    public EmailService(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;
    }

    public void sendConfirmationEmail(String to, String subject, String htmlContent) {
        // O remetente DEVE ser um email verificado no SendGrid
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);

        // O conteúdo HTML
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, toEmail, content);

        try {
            // Adiciona as imagens inline como Attachments (Attachments Inline)

            // 1. Imagem de fundo 'imagem.png'
            Attachments backgroundImage = createInlineAttachment("imagem.png", "image/png", "imagemCorraAgil");
            mail.addAttachments(backgroundImage);

            // 2. Logo 'logo.png'
            Attachments logoImage = createInlineAttachment("logo.png", "image/png", "logoCorraAgil");
            mail.addAttachments(logoImage);

        } catch (IOException e) {
            logger.error("Falha ao ler recursos de imagem para o SendGrid", e);
            // Decide se deve abortar ou continuar sem imagens. Vamos continuar por padrão.
        }

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGridClient.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                logger.info("E-mail com o assunto '{}' enviado com sucesso para '{}'. Status: {}",
                        subject, to, response.getStatusCode());
            } else {
                logger.error("Falha ao enviar e-mail com o assunto '{}' para '{}'. Status: {}, Body: {}",
                        subject, to, response.getStatusCode(), response.getBody());
            }

        } catch (IOException e) {
            logger.error("Falha de conexão/API ao enviar e-mail com o assunto '{}' para '{}'.", subject, to, e);
        }
    }

    /**
     * Cria um objeto Attachments para ser usado como imagem inline no SendGrid.
     * @param resourceName Nome do arquivo (ex: "logo.png")
     * @param mimeType Tipo MIME do arquivo (ex: "image/png")
     * @param contentId O ID (CID) usado na tag <img> do HTML (ex: "logoCorraAgil")
     * @return Objeto Attachments configurado para envio inline.
     * @throws IOException Se o recurso não puder ser lido.
     */
    private Attachments createInlineAttachment(String resourceName, String mimeType, String contentId) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourceName);
        byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String base64Content = Base64.getEncoder().encodeToString(fileBytes);

        Attachments attachment = new Attachments();
        attachment.setFilename(resourceName);
        attachment.setType(mimeType);
        attachment.setDisposition("inline"); // IMPORTANTE: Define como conteúdo inline
        attachment.setContentId(contentId);  // IMPORTANTE: Define o Content-ID
        attachment.setContent(base64Content);

        return attachment;
    }
}