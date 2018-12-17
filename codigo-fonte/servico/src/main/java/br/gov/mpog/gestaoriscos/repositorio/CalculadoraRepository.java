package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Calculadora entity.
 */
public interface CalculadoraRepository extends JpaRepository<Calculadora, Long> {

    Calculadora findByTipoNomeIgnoreCaseAndProcessoIsNull(String tipoCalculadoraNome);

    List<Calculadora> findByProcessoIsNull();

    Calculadora findByTipoNomeIgnoreCaseAndProcessoId(String tipoCalculadoraNome, Long processoId);

    List<Calculadora> findByProcessoId(Long processoId);
}