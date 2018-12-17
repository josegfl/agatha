package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.TipoControle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TipoControle entity.
 */
public interface TipoControleRepository extends JpaRepository<TipoControle, Long>{

    TipoControle findByNomeIgnoreCase(String nome);

}