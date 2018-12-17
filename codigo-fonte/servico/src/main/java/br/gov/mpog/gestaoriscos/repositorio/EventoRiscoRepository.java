package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.EventoRisco;
import br.gov.mpog.gestaoriscos.modelo.RespostaRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EventoRisco entity.
 */
public interface EventoRiscoRepository extends JpaRepository<EventoRisco, Long> {

    Page<EventoRisco> findByIdentificacaoProcessoIdOrderByIdAsc(Long identificacaoId, Pageable pageable);

    List<EventoRisco> findByIdentificacaoProcessoIdOrderByIdAsc(Long processoId);

    Long countByIdentificacaoProcessoId(Long processoId);

    List<EventoRisco> findByEventoId(Long id);

    @Modifying
    @Query("UPDATE EventoRisco er SET er.respostaRisco = :respostaRisco, er.justificativaRespostaRisco = :justificativa where er.id = :eventoRiscoId")
    void saveRespostaRisco(@Param("eventoRiscoId") Long eventoRiscoId, @Param("respostaRisco") RespostaRisco respostaRisco, @Param("justificativa") String justificativaRespostaRisco);
}