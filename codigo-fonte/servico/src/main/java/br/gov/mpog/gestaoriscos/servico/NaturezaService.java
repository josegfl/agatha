package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Natureza.
 */
public interface NaturezaService{

    /**
     * Save a natureza.
     *
     * @param naturezaDTO the entity to save
     * @return the persisted entity
     */
    NaturezaDTO save(NaturezaDTO naturezaDTO);

    /**
     * Get all the naturezas.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<NaturezaDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" natureza.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NaturezaDTO findOne(Long id);

    /**
     * Delete the "id" natureza.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Natureza com a mesma descrição
     *
     * @param naturezaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(NaturezaDTO naturezaDTO);

    List<String> searchByDescricao(String descricao);

}
