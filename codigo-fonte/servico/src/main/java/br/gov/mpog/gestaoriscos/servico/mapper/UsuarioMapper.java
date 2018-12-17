package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Usuario and its DTO UsuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class, PermissaoMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "orgao", ignore = true)
    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    List<UsuarioDTO> usuariosToUsuarioDTOs(List<Usuario> usuarios);

    Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO);

    List<Usuario> usuarioDTOsToUsuarios(List<UsuarioDTO> usuarioDTOs);
}
