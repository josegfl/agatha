package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.EventoConsequencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the EventoConsequencia entity.
 */
public interface EventoConsequenciaRepository extends JpaRepository<EventoConsequencia, Long>{

    List<EventoConsequencia> findByConsequenciaId(Long id);
}