package br.com.pipocaagil.corraagil.dto;

import lombok.Data; // Se estiver usando Lombok
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    public void setEmail(String email) {
        // Verifica se o valor não é nulo antes de chamar toUpperCase()
        if (email != null) {
            this.email = email.toUpperCase(); // <--- A conversão acontece AQUI!
        } else {
            this.email = null;
        }
    }
}