package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.TipoCalculadora;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TipoCalculadora entity.
 */
public interface TipoCalculadoraRepository extends JpaRepository<TipoCalculadora, Long>{

}