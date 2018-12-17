package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Avaliacao entity.
 */
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>{

    Avaliacao findByProcessoId(Long processoId);
}