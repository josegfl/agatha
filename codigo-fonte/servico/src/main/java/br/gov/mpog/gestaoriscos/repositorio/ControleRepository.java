package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Controle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Controle entity.
 */
public interface ControleRepository extends JpaRepository<Controle, Long>{

    Controle findBySearchIgnoreCaseAndOrgaoIdIsNull(String descricao);

    Page<Controle> findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Controle> findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

}