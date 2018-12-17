package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity Perfil and its DTO PerfilDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PerfilMapper {

    PerfilDTO perfilToPerfilDTO(Perfil perfil);

    List<PerfilDTO> perfilsToPerfilDTOs(List<Perfil> perfils);

    Perfil perfilDTOToPerfil(PerfilDTO perfilDTO);

    List<Perfil> perfilDTOsToPerfils(List<PerfilDTO> perfilDTOs);

}
