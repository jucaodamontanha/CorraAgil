package br.com.pipocaagil.corraagil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Entidade que representa uma corrida.
 */
@Entity
@Data // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // Gera construtor padrão sem argumentos
@AllArgsConstructor // Gera construtor com todos os argumentos
@Table(name = "corridas")
public class CorridaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Use tipos de dados mais apropriados
    private Duration duration;
    private Double distance;
    private Integer calories;

    // Relacionamento com CadastroModel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadastro_id", nullable = false)
    private CadastroModel cadastro;
}