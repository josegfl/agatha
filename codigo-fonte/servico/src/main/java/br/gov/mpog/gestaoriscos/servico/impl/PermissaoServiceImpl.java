package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.Permissao;
import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoContainerDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.PerfilRepository;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.impl.UsuarioCustomRepositorioImpl;
import br.gov.mpog.gestaoriscos.servico.PermissaoService;
import br.gov.mpog.gestaoriscos.servico.mapper.PerfilMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.PermissaoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing Permissao.
 */
@Service
@Transactional
public class PermissaoServiceImpl implements PermissaoService {

    private final Logger log = LoggerFactory.getLogger(PermissaoServiceImpl.class);

    private final PermissaoRepository permissaoRepository;

    private final PerfilRepository perfilRepository;

    private final PermissaoCustomRepositorio permissaoCustomRepositorio;

    private final UsuarioCustomRepositorioImpl usuarioCustomRepositorio;

    private final OrgaoCustomRepositorio orgaoCustomRepositorio;

    private final PermissaoMapper permissaoMapper;

    private final PerfilMapper perfilMapper;

    private final UsuarioMapper usuarioMapper;

    @Autowired
    public PermissaoServiceImpl(PermissaoRepository permissaoRepository, PerfilRepository perfilRepository, PermissaoCustomRepositorio permissaoCustomRepositorio, UsuarioCustomRepositorioImpl usuarioCustomRepositorio, OrgaoCustomRepositorio orgaoCustomRepositorio, PermissaoMapper permissaoMapper, PerfilMapper perfilMapper, UsuarioMapper usuarioMapper) {
        this.permissaoRepository = permissaoRepository;
        this.perfilRepository = perfilRepository;
        this.permissaoCustomRepositorio = permissaoCustomRepositorio;
        this.usuarioCustomRepositorio = usuarioCustomRepositorio;
        this.orgaoCustomRepositorio = orgaoCustomRepositorio;
        this.permissaoMapper = permissaoMapper;
        this.perfilMapper = perfilMapper;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Get all the usuarioDTOs.
     *
     * @return the list of usuarioDTOs
     */
    @Override
    public List<String> searchUsuarioByNome(String nome) {
        log.debug("Request to get all Usuarios by nome");
        return usuarioCustomRepositorio.searchByDescricao(nome);
    }

    @Override
    public List<String> searchOrgaoByNome(String nome) {
        return orgaoCustomRepositorio.searchByNomeAndOrgaoPaiId(nome, 2981L);
    }

    /**
     * Get all the perfilDTOs.
     *
     * @return the list of perfilDTOs
     */
    @Override
    public List<PerfilDTO> findAllPerfils() {
        log.debug("Request to get all Perfils");
        List<Perfil> perfils = perfilRepository.findAll();
        return perfilMapper.perfilsToPerfilDTOs(perfils);
    }

    /**
     * Save a permissao.
     *
     * @param permissaoContainerDTO the entity to save
     */
    @Override
    public void saveList(PermissaoContainerDTO permissaoContainerDTO) {
        savePermissaoList(permissaoContainerDTO.getPermissaos());
    }

    /**
     * Save a permissao.
     *
     * @param permissaoContainerDTO the entity to save
     */
    @Override
    public void saveList(List<PermissaoDTO> permissoesDTO) {
        savePermissaoList(permissoesDTO);
    }

    public void savePermissaoList(List<PermissaoDTO> permissoesDTO) {
        log.debug("Request to save Permissao : {}", permissoesDTO);
        List<Permissao> permissoes = permissaoMapper.permissaoDTOsToPermissaos(permissoesDTO);
        for (Permissao permissao : permissoes) {

            if (permissao.getId() == null) {
                permissao.setDtCadastro(new Date());
            }

            permissaoRepository.save(permissao);
        }
    }

    /**
     * Save a permissao.
     *
     * @param permissaoDTO the entity to save
     */
    @Override
    public PermissaoDTO save(PermissaoDTO permissaoDTO) {
        log.debug("Request to save Permissao : {}", permissaoDTO);
        Permissao permissao = permissaoMapper.permissaoDTOToPermissao(permissaoDTO);
        permissaoRepository.save(permissao);
        return permissaoDTO;
    }

    @Override
    public Page<UsuarioDTO> findAll(String usuario, Long perfilId, Long orgao, Pageable pageable) {
        log.debug("Request to get all Permissaos");
        Page<Usuario> result;
        result = permissaoCustomRepositorio.listarPermissaos(pageable, usuario, perfilId, orgao);

        return result.map(usuarioMapper::usuarioToUsuarioDTO);
    }

    /**
     * Get one permissao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PermissaoDTO findOne(Long id) {
        log.debug("Request to get Permissao : {}", id);
        Permissao permissao = permissaoRepository.findOne(id);
        return new PermissaoDTO(permissao);
    }

    /**
     * Delete the  permissao by list id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deleteList(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return;
        }
        log.debug("Request to delete Permissao : {}", idList);
        permissaoRepository.deletePermissaoById(idList);
    }

    /**
     * Delete the  permissao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        log.debug("Request to delete Permissao : {}", id);
        permissaoRepository.deletePermissaoById(id);
    }

    @Override
    public Boolean verificarExistencia(PermissaoDTO permissaoDTO) {
        Permissao permissaoPersisted = permissaoRepository.findByUsuarioIdAndPerfilId(permissaoDTO.getUsuario().getId(), permissaoDTO.getPerfil().getId());
        return !(permissaoPersisted == null || permissaoPersisted.getId().equals(permissaoDTO.getId()));
    }

    @Override
    public List<PermissaoDTO> getPermissoesByCPF(String cpf) {
        List<Permissao> result = permissaoRepository.findByUsuario_Cpf(cpf);
        return permissaoMapper.permissaosToPermissaoDTOs(result);
    }
}
