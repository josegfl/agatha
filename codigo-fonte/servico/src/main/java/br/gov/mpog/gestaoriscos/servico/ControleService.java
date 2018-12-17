package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Controle.
 */
public interface ControleService{

    /**
     * Save a controle.
     *
     * @param controleDTO the entity to save
     * @return the persisted entity
     */
    ControleDTO save(ControleDTO controleDTO);

    /**
     * Get all the controles.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<ControleDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" controle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ControleDTO findOne(Long id);

    /**
     * Delete the "id" controle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Controle com a mesma descrição
     *
     * @param controleDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(ControleDTO controleDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);

    Boolean hasTaxonomia(Long id);
}
