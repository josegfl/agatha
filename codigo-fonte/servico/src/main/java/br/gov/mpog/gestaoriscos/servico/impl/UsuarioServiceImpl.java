package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.repositorio.UsuarioRepository;
import br.gov.mpog.gestaoriscos.servico.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.repositorio.UsuarioRepository;
import br.gov.mpog.gestaoriscos.servico.PermissaoService;
import br.gov.mpog.gestaoriscos.servico.UsuarioService;
import br.gov.mpog.gestaoriscos.servico.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(PermissaoServiceImpl.class);

    private final UsuarioMapper usuarioMapper;

    private final PermissaoService permissaoService;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, PermissaoService permissaoService) {
        this.usuarioMapper = usuarioMapper;
        this.permissaoService = permissaoService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método responsável por realizar o salvamento de um usuário
     *
     * @param usuarioDTO
     * @return UsuarioDTO
     */
    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        usuario.setOrgao(new Orgao());
        usuario.getOrgao().setId(usuarioDTO.getOrgao().getId());
        Usuario usuarioPersistido = usuarioRepository.save(usuario);
        updateIdUsuarioPermissao(usuarioDTO.getPermissoes(), usuarioPersistido);
        permissaoService.saveList(usuarioDTO.getPermissoes());
        return usuarioMapper.usuarioToUsuarioDTO(usuarioPersistido);
    }

    /**
     * Método responsável por atualizar os dados de um usuário
     *
     * @param usuarioDTO
     * @return UsuarioDTO
     */
    @Override
    public UsuarioDTO update(UsuarioDTO usuarioDTO) {
        log.debug("Request to update Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
        usuario.setOrgao(new Orgao());
        usuario.getOrgao().setId(usuarioDTO.getOrgao().getId());
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        updatePermissaoList(usuarioDTO.getPermissoes(), usuarioAtualizado);
        return usuarioMapper.usuarioToUsuarioDTO(usuarioAtualizado);
    }

    /**
     * Método responsável por verificar se o CPF informado já exite na base de dados
     *
     * @param cpf
     * @return
     */
    @Override
    public Boolean isCPFCadastrado(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf);
        return usuario != null;
    }

    @Override
    public Boolean isCPFCadastradoDiferenteIdUsuario(String cpf, Long idUsuario) {
        Usuario usuario = usuarioRepository.findByCpfExcludeId(cpf, idUsuario);
        return usuario != null;
    }

    /**
     * Método responsável por recuperar o usuário pelo CPF
     *
     * @param cpf
     * @return
     */
    @Override
    public UsuarioDTO findByCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf);
        return usuarioMapper.usuarioToUsuarioDTO(usuario);
    }

    /**
     * Método responsável por recurar o usuário pelo ID
     *
     * @param id
     * @return
     */
    @Override
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        UsuarioDTO dto = usuarioMapper.usuarioToUsuarioDTO(usuario);
        if (!Objects.isNull(usuario.getOrgao())) {
            dto.setOrgao(new OrgaoDTO());
            dto.getOrgao().setId(usuario.getOrgao().getId());
            dto.getOrgao().setNome(usuario.getOrgao().getNome());
            dto.getOrgao().setSigla(usuario.getOrgao().getSigla());
        }
        return dto;
    }

    /**
     * Método responsável por atualizar as permissões de um usuário
     *
     * @param permissoesList
     * @param usuario
     */
    private void updatePermissaoList(List<PermissaoDTO> permissoesList, Usuario usuario) {
        updateIdUsuarioPermissao(permissoesList, usuario);
        permissaoService.deleteList(permissoesList.stream()
                .filter(PermissaoDTO::isExcluido)
                .map(PermissaoDTO::getId)
                .collect(Collectors.toList()));
        permissaoService.saveList(permissoesList.stream()
                .filter(permissaoDTO -> !permissaoDTO.isExcluido())
                .collect(Collectors.toList()));
    }

    /**
     * Método responsável por injetar o usuário em uma lista de permissões
     *
     * @param permissoesList
     * @param usuario
     */
    private void updateIdUsuarioPermissao(List<PermissaoDTO> permissoesList, Usuario usuario) {
        for (PermissaoDTO permissaoDTO: permissoesList) {
            permissaoDTO.setUsuario(new UsuarioDTO());
            permissaoDTO.getUsuario().setId(usuario.getId());
        }
    }

}
