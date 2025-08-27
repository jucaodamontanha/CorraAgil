package br.com.pipocaagil.corraagil.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequestDTO {
    @NotBlank(message = "O token é obrigatório")
    private String token;
}