package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Evento and its DTO EventoDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class})
public interface EventoMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "orgao", ignore = true)
    EventoDTO eventoToEventoDTO(Evento evento);

    List<EventoDTO> eventosToEventoDTOs(List<Evento> eventos);

    Evento eventoDTOToEvento(EventoDTO eventoDTO);

    List<Evento> eventoDTOsToEventos(List<EventoDTO> eventoDTOs);
}