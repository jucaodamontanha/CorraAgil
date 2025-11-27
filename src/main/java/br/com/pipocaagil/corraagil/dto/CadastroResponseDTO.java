package br.com.pipocaagil.corraagil.dto;

/**
 * DTO para enviar dados de resposta de cadastro.
 */
public class CadastroResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String senha;

    // Construtor padrão
    public CadastroResponseDTO() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}