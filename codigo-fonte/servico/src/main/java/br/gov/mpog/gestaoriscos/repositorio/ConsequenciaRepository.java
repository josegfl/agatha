package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Consequencia entity.
 */
@SuppressWarnings("unused")
public interface ConsequenciaRepository extends JpaRepository<Consequencia, Long> {

    Consequencia findBySearchIgnoreCaseAndOrgaoIdIsNull(String descricao);

    Page<Consequencia> findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Consequencia> findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

}
