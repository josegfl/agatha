package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.DecisaoProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the DecisaoProcesso entity.
 */
public interface DecisaoProcessoRepository extends JpaRepository<DecisaoProcesso, Long> {

    List<DecisaoProcesso> findByOrderByNomeAsc();

    DecisaoProcesso findByNomeIgnoreCase(String nome);

}