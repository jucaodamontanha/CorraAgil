package br.com.pipocaagil.corraagil.Reset;

import br.com.pipocaagil.corraagil.Cadastro.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar operações de persistência de ResetToken.
 */
@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    /**
     * Busca um ResetToken pelo token.
     *
     * @param token token de reset
     * @return ResetToken correspondente ao token
     */
    ResetToken findByToken(String token);

    /**
     * Busca um ResetToken pelo cadastro.
     *
     * @param cadastroModel cadastro associado ao token
     * @return ResetToken correspondente ao cadastro
     */
    ResetToken findByCadastroModel(CadastroModel cadastroModel);
}