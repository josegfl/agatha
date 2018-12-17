package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Categoria entity.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    Categoria findBySearchIgnoreCase(String descricao);

    Page<Categoria> findBySearchContainingIgnoreCaseOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Categoria> findBySearchContainingIgnoreCaseAndStatusOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

    List<Categoria> findByStatusTrueOrderByDescricaoAsc();
}