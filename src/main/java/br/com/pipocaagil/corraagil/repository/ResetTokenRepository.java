package br.com.pipocaagil.corraagil.repository;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.model.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para gerenciar operações de persistência de ResetToken.
 */
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    Optional<ResetToken> findByToken(String token);
    Optional<ResetToken> findByCadastroModel(CadastroModel cadastroModel);
}