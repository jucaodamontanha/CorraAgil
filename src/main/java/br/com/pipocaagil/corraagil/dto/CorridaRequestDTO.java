package br.com.pipocaagil.corraagil.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorridaRequestDTO {
    @NotNull(message = "O tempo da corrida é obrigatório")
    private Duration duration;

    @NotNull(message = "A distância da corrida é obrigatória")
    private Double distance;

    @NotNull(message = "As calorias são obrigatórias")
    private Integer calories;
}