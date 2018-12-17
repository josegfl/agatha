package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.TipoTaxonomia;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TipoTaxonomia entity.
 */
public interface TipoTaxonomiaRepository extends JpaRepository<TipoTaxonomia, Long>{

    TipoTaxonomia findByNomeIgnoreCase(String nome);

}