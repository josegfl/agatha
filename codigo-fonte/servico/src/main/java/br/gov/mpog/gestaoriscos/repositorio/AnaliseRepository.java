package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Analise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Analise entity.
 */
public interface AnaliseRepository extends JpaRepository<Analise, Long>{

}