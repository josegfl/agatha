package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoContainerDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Permissao.
 */
public interface PermissaoService{

    /**
     * Get all the PerfilDTOs.
     *
     * @return the list of PerfilDTOs
     */
    List<PerfilDTO> findAllPerfils();

    /**
     * Save a list of permissao.
     *
     * @param permissaoContainerDTO the entity to save
     */
    void saveList(PermissaoContainerDTO permissaoContainerDTO);

    /**
     * Save a list of permissao.
     *
     * @param permissaoContainerDTO the entity to save
     */
    void saveList(List<PermissaoDTO> permissoesDTO);

    /**
     * Save a permissao.
     *
     * @param permissaoDTO the entity to save
     */
    PermissaoDTO save(PermissaoDTO permissaoDTO);

    /**
     * Get all the permissaos.
     *   @param usuario
     * @param orgao
     * @param pageable the pagination information  @return the list of entities
     */
    Page<UsuarioDTO> findAll(String usuario, Long perfilId, Long orgao, Pageable pageable);

    /**
     * Get the "id" permissao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PermissaoDTO findOne(Long id);

    /**
     * Delete the "id" by list permissao.
     *
     * @param id the id of the entity
     */
    void deleteList(List<Long> idList);

    /**
     * Delete the "id" permissao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the usuarioDTOs.
     *
     * @return the list of usuarioDTOs
     */
    List<String> searchUsuarioByNome(String nome);

    List<String> searchOrgaoByNome(String nome);

    Boolean verificarExistencia(PermissaoDTO permissaoDTO);

    List<PermissaoDTO> getPermissoesByCPF(String cpf);
}
