package br.com.pipocaagil.corraagil.repository;

import br.com.pipocaagil.corraagil.model.CorridaModel;
import br.com.pipocaagil.corraagil.model.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para gerenciar operações de persistência de CorridaModel.
 */
@Repository
public interface CorridaRepository extends JpaRepository<CorridaModel, Long> {

    // Spring Data JPA entende a intenção deste método
    // e o implementa automaticamente, buscando corridas por um cadastro.
    List<CorridaModel> findByCadastro(CadastroModel cadastro);
}