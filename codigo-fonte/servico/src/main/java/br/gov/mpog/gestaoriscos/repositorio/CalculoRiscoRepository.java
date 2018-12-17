package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.CalculoRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring Data JPA repository for the CalculoRisco entity.
 */
public interface CalculoRiscoRepository extends JpaRepository<CalculoRisco, Long> {

    @Query("SELECT COUNT(cr.nivel) FROM CalculoRisco cr INNER JOIN cr.eventoRisco er INNER JOIN er.identificacao id INNER JOIN id.processo pro WHERE pro.id = :processoId")
    Long countNiveisByProcessoId(@Param("processoId") Long processoId);

}