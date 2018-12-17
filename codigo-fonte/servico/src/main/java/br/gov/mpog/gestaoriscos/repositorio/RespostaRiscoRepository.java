package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.RespostaRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the RespostaRisco entity.
 */
public interface RespostaRiscoRepository extends JpaRepository<RespostaRisco, Long>{

    RespostaRisco findByNomeIgnoreCase(String nome);

    @Query("SELECT COUNT(rr.id) FROM RespostaRisco rr INNER JOIN rr.eventos er INNER JOIN er.identificacao id INNER JOIN id.processo pro WHERE pro.id = :processoId")
    Long countByProcessoId(@Param("processoId") Long processoId);
}