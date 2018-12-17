package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.StatusProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the StatusProcesso entity.
 */
public interface StatusProcessoRepository extends JpaRepository<StatusProcesso, Long>{

    StatusProcesso findByNomeIgnoreCase(String nome);

}