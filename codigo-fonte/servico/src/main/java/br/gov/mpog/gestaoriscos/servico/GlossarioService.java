package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.GlossarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Glossario.
 */
public interface GlossarioService{

    /**
     * Save a glossario.
     *
     * @param glossarioDTO the entity to save
     * @return the persisted entity
     */
    GlossarioDTO save(GlossarioDTO glossarioDTO);

    /**
     * Get all the glossarios.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<GlossarioDTO> findAll(Pageable pageable, String termo, String descricao, Boolean status);

    /**
     * Get the "id" glossario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GlossarioDTO findOne(Long id);

    /**
     * Delete the "id" glossario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Glossario com a mesma descrição
     *
     * @param glossarioDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(GlossarioDTO glossarioDTO);

    List<String> searchByTermo(String descricao);

    List<String> searchByDescricao(String descricao);

    List<GlossarioDTO> findAll();
}