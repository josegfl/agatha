package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.EventoConsequencia;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoConsequenciaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity EventoConsequencia and its DTO EventoConsequenciaDTO.
 */
@Mapper(componentModel = "spring", uses = {EventoRiscoMapper.class, ConsequenciaMapper.class})
public interface EventoConsequenciaMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "eventoRisco", ignore = true)
    EventoConsequenciaDTO eventoConsequenciaToEventoConsequenciaDTO(EventoConsequencia eventoConsequencia);

    List<EventoConsequenciaDTO> eventoConsequenciasToEventoConsequenciaDTOs(List<EventoConsequencia> eventoConsequencias);

    @Mapping(target = "eventoRisco", ignore = true)
    EventoConsequencia eventoConsequenciaDTOToEventoConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO);

    List<EventoConsequencia> eventoConsequenciaDTOsToEventoConsequencias(List<EventoConsequenciaDTO> eventoConsequenciaDTOs);


}
