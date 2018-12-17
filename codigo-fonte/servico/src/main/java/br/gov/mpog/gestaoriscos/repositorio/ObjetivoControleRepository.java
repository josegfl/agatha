package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.ObjetivoControle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ObjetivoControle entity.
 */
public interface ObjetivoControleRepository extends JpaRepository<ObjetivoControle, Long>{

    ObjetivoControle findByNomeIgnoreCase(String nome);

}