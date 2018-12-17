package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Categoria.
 */
public interface CategoriaService{

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<CategoriaDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" categoria.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategoriaDTO findOne(Long id);

    /**
     * Delete the "id" categoria.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Categoria com a mesma descrição
     *
     * @param categoriaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(CategoriaDTO categoriaDTO);

    List<String> searchByDescricao(String descricao);

    List<NaturezaDTO> findAllNatureza();

    Boolean hasProcesso(Long id);
}
