package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.StatusProcesso;
import br.gov.mpog.gestaoriscos.modelo.dto.StatusProcessoDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity StatusProcesso and its DTO StatusProcessoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatusProcessoMapper{

    StatusProcessoDTO statusProcessoToStatusProcessoDTO(StatusProcesso statusProcesso);

    List<StatusProcessoDTO> statusProcessosToStatusProcessoDTOs(List<StatusProcesso> statusProcessos);

    StatusProcesso statusProcessoDTOToStatusProcesso(StatusProcessoDTO statusProcessoDTO);

    List<StatusProcesso> statusProcessoDTOsToStatusProcessos(List<StatusProcessoDTO> statusProcessoDTOs);
}
