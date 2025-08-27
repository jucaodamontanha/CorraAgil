package br.com.pipocaagil.corraagil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordResetRequestDTO {
    @NotBlank(message = "O token é obrigatório")
    private String token;

    @NotBlank(message = "A nova senha é obrigatória")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
            message = "A senha deve ter no mínimo 8 caracteres, pelo menos um caractere especial e uma letra maiúscula.")
    private String novaSenha;
}