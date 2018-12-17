package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.AgrupamentoTaxonomia;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AgrupamentoTaxonomia entity.
 */
public interface AgrupamentoTaxonomiaRepository extends JpaRepository<AgrupamentoTaxonomia, Long>{

}