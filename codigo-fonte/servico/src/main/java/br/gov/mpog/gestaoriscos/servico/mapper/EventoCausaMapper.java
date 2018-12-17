package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.EventoCausa;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoCausaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity EventoCausa and its DTO EventoCausaDTO.
 */
@Mapper(componentModel = "spring", uses = {EventoRiscoMapper.class, CausaMapper.class})
public interface EventoCausaMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "eventoRisco", ignore = true)
    EventoCausaDTO eventoCausaToEventoCausaDTO(EventoCausa eventoCausa);

    List<EventoCausaDTO> eventoCausasToEventoCausaDTOs(List<EventoCausa> eventoCausas);

    @Mapping(target = "eventoRisco", ignore = true)
    EventoCausa eventoCausaDTOToEventoCausa(EventoCausaDTO eventoCausaDTO);

    List<EventoCausa> eventoCausaDTOsToEventoCausas(List<EventoCausaDTO> eventoCausaDTOs);


}
