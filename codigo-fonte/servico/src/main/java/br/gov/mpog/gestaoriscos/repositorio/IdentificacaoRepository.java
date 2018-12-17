package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Identificacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Identificacao entity.
 */
public interface IdentificacaoRepository extends JpaRepository<Identificacao, Long>{

    Identificacao findByProcessoId(Long id);
}