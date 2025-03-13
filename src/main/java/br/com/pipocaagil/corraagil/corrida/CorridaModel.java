package br.com.pipocaagil.corraagil.corrida;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma corrida.
 */
@Entity
public class CorridaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Duration duracao;
    private boolean pausada;

    /**
     * Construtor com parâmetros.
     *
     * @param id ID da corrida
     * @param inicio Data e hora de início da corrida
     * @param fim Data e hora de fim da corrida
     * @param pausada Indica se a corrida está pausada
     */
    public CorridaModel(Long id, LocalDateTime inicio, LocalDateTime fim, boolean pausada) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
        this.pausada = pausada;
        if (this.inicio != null && this.fim != null) {
            this.duracao = Duration.between(this.inicio, this.fim);
        }
    }

    /**
     * Construtor padrão.
     */
    public CorridaModel() {}

    @Override
    public String toString() {
        return "CorridaModel{" +
                "id=" + id +
                ", inicio=" + inicio +
                ", fim=" + fim +
                ", duracao=" + duracao +
                ", pausada=" + pausada +
                '}';
    }

    /**
     * Obtém o ID da corrida.
     *
     * @return ID da corrida
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da corrida.
     *
     * @param id ID da corrida
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém a data e hora de início da corrida.
     *
     * @return Data e hora de início da corrida
     */
    public LocalDateTime getInicio() {
        return inicio;
    }

    /**
     * Define a data e hora de início da corrida.
     *
     * @param inicio Data e hora de início da corrida
     */
    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    /**
     * Obtém a data e hora de fim da corrida.
     *
     * @return Data e hora de fim da corrida
     */
    public LocalDateTime getFim() {
        return fim;
    }

    /**
     * Define a data e hora de fim da corrida.
     *
     * @param fim Data e hora de fim da corrida
     */
    public void setFim(LocalDateTime fim) {
        this.fim = fim;
        if (this.inicio != null && this.fim != null) {
            this.duracao = Duration.between(this.inicio, this.fim);
        }
    }

    /**
     * Obtém a duração da corrida.
     *
     * @return Duração da corrida
     */
    public Duration getDuracao() {
        return duracao;
    }

    /**
     * Define a duração da corrida.
     *
     * @param duracao Duração da corrida
     */
    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    /**
     * Verifica se a corrida está pausada.
     *
     * @return true se a corrida estiver pausada, false caso contrário
     */
    public boolean isPausada() {
        return pausada;
    }

    /**
     * Define se a corrida está pausada.
     *
     * @param pausada true se a corrida estiver pausada, false caso contrário
     */
    public void setPausada(boolean pausada) {
        this.pausada = pausada;
    }
}