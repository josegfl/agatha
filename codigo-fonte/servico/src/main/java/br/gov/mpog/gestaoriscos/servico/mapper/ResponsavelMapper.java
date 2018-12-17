package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Responsavel;
import br.gov.mpog.gestaoriscos.modelo.dto.ResponsavelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Responsavel and its DTO ResponsavelDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, ProcessoMapper.class})
public interface ResponsavelMapper{

    @Mapping(target = "processo", ignore = true)
    ResponsavelDTO responsavelToResponsavelDTO(Responsavel responsavel);

    List<ResponsavelDTO> responsavelToResponsavelDTOs(List<Responsavel> responsavel);

    @Mapping(target = "processo", ignore = true)
    Responsavel responsavelDTOToResponsavel(ResponsavelDTO responsavelDTO);

    List<Responsavel> responsavelDTOsToResponsavels(List<ResponsavelDTO> responsavelDTOs);
}
