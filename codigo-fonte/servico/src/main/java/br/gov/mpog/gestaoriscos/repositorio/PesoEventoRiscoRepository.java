package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.PesoEventoRisco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the PesoEventoRisco entity.
 */
public interface PesoEventoRiscoRepository extends JpaRepository<PesoEventoRisco, Long>{

}