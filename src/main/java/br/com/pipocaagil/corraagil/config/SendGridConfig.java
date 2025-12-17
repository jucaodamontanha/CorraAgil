package br.com.pipocaagil.corraagil.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    // O Spring irá injetar o valor da variável de ambiente SENDGRID_API_KEY
    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    @Bean
    public SendGrid sendGridClient() {
        return new SendGrid(sendGridApiKey);
    }
}