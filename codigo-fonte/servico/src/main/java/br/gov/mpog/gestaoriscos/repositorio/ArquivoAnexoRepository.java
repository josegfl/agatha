package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.ArquivoAnexo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ArquivoAnexo entity.
 */
public interface ArquivoAnexoRepository extends JpaRepository<ArquivoAnexo, Long>{

}