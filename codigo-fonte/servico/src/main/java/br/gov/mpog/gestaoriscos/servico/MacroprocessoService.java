package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Macroprocesso.
 */
public interface MacroprocessoService {

    /**
     * Save a macroprocesso.
     *
     * @param macroprocessoDTO the entity to save
     * @return the persisted entity
     */
    MacroprocessoDTO save(MacroprocessoDTO macroprocessoDTO);

    /**
     * Get all the macroprocessos.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<MacroprocessoDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" macroprocesso.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MacroprocessoDTO findOne(Long id);

    /**
     * Delete the "id" macroprocesso.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Macroprocesso com a mesma descrição
     *
     * @param macroprocessoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(MacroprocessoDTO macroprocessoDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);
}
