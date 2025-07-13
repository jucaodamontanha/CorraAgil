package br.com.pipocaagil.corraagil.Reset;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Entidade que representa um token de reset de senha.
 */
@Entity
public class ResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(targetEntity = CadastroModel.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "cadastro_id")
    private CadastroModel cadastroModel;
    private Date expiryDate;

    /**
     * Construtor padrão.
     */
    public ResetToken() {}

    /**
     * Construtor com parâmetros.
     *
     * @param token token gerado
     * @param cadastroModel cadastro associado ao token
     */
    public ResetToken(String token, CadastroModel cadastroModel) {
        this.token = token;
        this.cadastroModel = cadastroModel;
        this.expiryDate = calculateExpiryDate(2 * 10); // Exemplo de 20 minutos de validade
    }

    /**
     * Calcula a data de expiração do token.
     *
     * @param expiryTimeInMinutes tempo de expiração em minutos
     * @return data de expiração
     */
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return calendar.getTime();
    }

    /**
     * Verifica se o token é válido.
     *
     * @return true se o token for válido, false caso contrário
     */
    public boolean isTokenValido() {
        return new Date().before(expiryDate); // Verifica se a data atual é antes da data de expiração
    }

    /**
     * Obtém o ID do token.
     *
     * @return ID do token
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do token.
     *
     * @param id ID do token
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o token.
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Define o token.
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Obtém o cadastro associado ao token.
     *
     * @return cadastro associado ao token
     */
    public CadastroModel getCadastroModel() {
        return cadastroModel;
    }

    /**
     * Define o cadastro associado ao token.
     *
     * @param cadastroModel cadastro associado ao token
     */
    public void setCadastroModel(CadastroModel cadastroModel) {
        this.cadastroModel = cadastroModel;
    }

    /**
     * Obtém a data de expiração do token.
     *
     * @return data de expiração do token
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Define a data de expiração do token.
     *
     * @param expiryDate data de expiração do token
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
