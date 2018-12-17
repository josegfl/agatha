package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.ImpactoCalculadora;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ImpactoCalculadora entity.
 */
public interface ImpactoCalculadoraRepository extends JpaRepository<ImpactoCalculadora, Long>{

}