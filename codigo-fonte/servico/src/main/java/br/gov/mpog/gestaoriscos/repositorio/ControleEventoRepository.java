package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the ControleEvento entity.
 */
public interface ControleEventoRepository extends JpaRepository<ControleEvento, Long>{

    List<ControleEvento> findByControleId(Long id);

    List<ControleEvento> findByDesenhoId(Long desenhoId);

    List<ControleEvento> findByOperacaoId(Long id);

    Long countByEventoRiscoIdentificacaoProcessoId(Long processoId);
}