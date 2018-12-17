package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxonomia entity.
 */
public interface TaxonomiaRepository extends JpaRepository<Taxonomia, Long> {

    List<Taxonomia> findByControleId(Long id);

    List<Taxonomia> findByCausaId(Long id);

    List<Taxonomia> findByConsequenciaId(Long id);

    List<Taxonomia> findByEventoId(Long id);
}