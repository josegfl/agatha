package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Natureza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Natureza entity.
 */
public interface NaturezaRepository extends JpaRepository<Natureza, Long>{

    Natureza findBySearchIgnoreCase(String descricao);

    Page<Natureza> findBySearchContainingIgnoreCaseOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Natureza> findBySearchContainingIgnoreCaseAndStatusOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

    List<Natureza> findByStatusTrueOrderByDescricaoAsc();
}