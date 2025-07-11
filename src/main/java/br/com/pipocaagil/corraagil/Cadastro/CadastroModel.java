package br.com.pipocaagil.corraagil.Cadastro;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
=======
>>>>>>> 20ebe67897f6aaf36f8fa490c8c2d4ca72bf77f4

/**
 * Entidade que representa um cadastro no sistema.
 */
@Entity
public class CadastroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomeCompleto;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
            message = "A senha deve ter no mínimo 8 caracteres, pelo menos um caractere especial e uma letra maiúscula.")
    private String senha;

    /**
     * Construtor com parâmetros.
     *
     * @param id ID do cadastro
     * @param nomeCompleto Nome completo do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     */
    public CadastroModel(Long id, String nomeCompleto, String email, String senha) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
    }

    /**
     * Construtor padrão.
     */
    public CadastroModel() {}

    /**
     * Obtém o ID do cadastro.
     *
     * @return ID do cadastro
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do cadastro.
     *
     * @param id ID do cadastro
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome completo do usuário.
     *
     * @return Nome completo do usuário
     */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * Define o nome completo do usuário.
     *
     * @param nomeCompleto Nome completo do usuário
     */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /**
     * Obtém o email do usuário.
     *
     * @return Email do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário.
     *
     * @param email Email do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return Senha do usuário
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     *
     * @param senha Senha do usuário
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}