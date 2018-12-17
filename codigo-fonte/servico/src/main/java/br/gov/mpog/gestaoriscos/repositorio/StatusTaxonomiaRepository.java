package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.StatusTaxonomia;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the StatusTaxonomia entity.
 */
public interface StatusTaxonomiaRepository extends JpaRepository<StatusTaxonomia, Long>{

    StatusTaxonomia findByNomeIgnoreCase(String nome);
}