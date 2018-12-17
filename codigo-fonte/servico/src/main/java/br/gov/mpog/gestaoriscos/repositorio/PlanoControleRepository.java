package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PlanoControle entity.
 */
public interface PlanoControleRepository extends JpaRepository<PlanoControle, Long>{

    List<PlanoControle> findByControleId(Long controleId);

    List<PlanoControle> findByEventoRiscoId(Long eventoRiscoId);

    Long countByEventoRiscoIdentificacaoProcessoId(Long processoId);
}