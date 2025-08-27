package br.com.pipocaagil.corraagil.repository;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para gerenciar operações de persistência de CadastroModel.
 */
@Repository
public interface CadastroRepository extends JpaRepository<CadastroModel, Long> {
    Optional<CadastroModel> findByEmail(String email);
}