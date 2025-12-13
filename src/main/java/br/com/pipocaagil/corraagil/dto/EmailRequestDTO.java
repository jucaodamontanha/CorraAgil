package br.com.pipocaagil.corraagil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDTO {
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    public void setEmail(String email) {
        // Verifica se o valor não é nulo antes de chamar toUpperCase()
        if (email != null) {
            this.email = email.toUpperCase(); // <--- A conversão acontece AQUI!
        } else {
            this.email = null;
        }
    }
}