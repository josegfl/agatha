package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.EventoCausa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the EventoCausa entity.
 */
public interface EventoCausaRepository extends JpaRepository<EventoCausa, Long>{

    List<EventoCausa> findByCausaId(Long id);
}