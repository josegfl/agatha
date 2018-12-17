package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Permissao;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Permissao and its DTO PermissaoDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, PerfilMapper.class})
public interface PermissaoMapper {

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "usuarioNome", source = "usuario.nome")
    PermissaoDTO permissaoToPermissaoDTO(Permissao permissao);

    List<PermissaoDTO> permissaosToPermissaoDTOs(List<Permissao> permissaos);

    Permissao permissaoDTOToPermissao(PermissaoDTO permissaoDTO);

    List<Permissao> permissaoDTOsToPermissaos(List<PermissaoDTO> permissaoDTOs);

}
