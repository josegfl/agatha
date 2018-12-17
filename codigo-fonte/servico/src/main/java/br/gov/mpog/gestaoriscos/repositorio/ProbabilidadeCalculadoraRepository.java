package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.ProbabilidadeCalculadora;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ProbabilidadeCalculadora entity.
 */
public interface ProbabilidadeCalculadoraRepository extends JpaRepository<ProbabilidadeCalculadora, Long>{

}