package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Causa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Causa entity.
 */
public interface CausaRepository extends JpaRepository<Causa, Long>{

    Causa findBySearchIgnoreCaseAndOrgaoIdIsNull(String descricao);

    Page<Causa> findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Causa> findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

}