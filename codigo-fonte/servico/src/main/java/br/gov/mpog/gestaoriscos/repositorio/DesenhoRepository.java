package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Desenho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Desenho entity.
 */
public interface DesenhoRepository extends JpaRepository<Desenho, Long>{

    Desenho findBySearchIgnoreCase(String descricao);

    Page<Desenho> findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Desenho> findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

    List<Desenho> findByStatusTrueOrderByDescricaoAsc();
}