package br.com.pipocaagil.corraagil.corrida;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Entidade que representa uma corrida.
 */
@Entity
public class CorridaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String time;
    private String distance;
    private String calories;

    public CorridaModel(Long id, String time, String distance, String calories) {
        this.id = id;
        this.time = time;
        this.distance = distance;
        this.calories = calories;
    }

    /**
     * Construtor padrão.
     */
    public CorridaModel() {}

    @Override
    public String toString() {
        return "CorridaModel{" +
                "id=" + id +
                ", time=" + time +
                ", distance='" + distance + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}