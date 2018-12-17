package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Causa.
 */
public interface CausaService {

    /**
     * Save a causa.
     *
     * @param causaDTO the entity to save
     * @return the persisted entity
     */
    CausaDTO save(CausaDTO causaDTO);

    /**
     * Get all the causas.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<CausaDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" causa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CausaDTO findOne(Long id);

    /**
     * Delete the "id" causa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Causa com a mesma descrição
     *
     * @param causaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(CausaDTO causaDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);

    Boolean hasTaxonomia(Long id);
}
