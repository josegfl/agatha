package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Consequencia.
 */
public interface ConsequenciaService {

    /**
     * Save a consequencia.
     *
     * @param consequenciaDTO the entity to save
     * @return the persisted entity
     */
    ConsequenciaDTO save(ConsequenciaDTO consequenciaDTO);

    /**
     * Get all the consequencias.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<ConsequenciaDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" consequencia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsequenciaDTO findOne(Long id);

    /**
     * Delete the "id" consequencia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Consequencia com a mesma descrição
     *
     * @param consequenciaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(ConsequenciaDTO consequenciaDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);

    Boolean hasTaxonomia(Long id);
}
