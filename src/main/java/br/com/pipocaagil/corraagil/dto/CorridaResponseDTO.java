package br.com.pipocaagil.corraagil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorridaResponseDTO {
    private Long id;
    private Duration duration;
    private Double distance;
    private Integer calories;
    private Long cadastroId; // Adicionamos o ID do cadastro para referência
}