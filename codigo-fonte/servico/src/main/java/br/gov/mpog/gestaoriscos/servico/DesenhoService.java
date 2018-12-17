package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.DesenhoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Desenho.
 */
public interface DesenhoService{

    /**
     * Save a desenho.
     *
     * @param desenhoDTO the entity to save
     * @return the persisted entity
     */
    DesenhoDTO save(DesenhoDTO desenhoDTO);

    /**
     * Get all the desenhos.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<DesenhoDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" desenho.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DesenhoDTO findOne(Long id);

    /**
     * Delete the "id" desenho.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Desenho com a mesma descrição
     *
     * @param desenhoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(DesenhoDTO desenhoDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);
}
