package br.com.pipocaagil.corraagil.repository;

import br.com.pipocaagil.corraagil.model.CorridaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar operações de persistência de CorridaModel.
 */
@Repository
public interface CorridaRepository extends JpaRepository<CorridaModel, Long> {
}