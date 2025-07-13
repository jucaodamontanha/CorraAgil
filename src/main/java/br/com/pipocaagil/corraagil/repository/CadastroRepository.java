package br.com.pipocaagil.corraagil.repository;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar operações de persistência de CadastroModel.
 */
@Repository
public interface CadastroRepository extends JpaRepository<CadastroModel, Long> {
    /**
     * Busca um CadastroModel pelo email.
     *
     * @param email Email do cadastro
     * @return CadastroModel correspondente ao email
     */
    CadastroModel findByEmail(String email);
}