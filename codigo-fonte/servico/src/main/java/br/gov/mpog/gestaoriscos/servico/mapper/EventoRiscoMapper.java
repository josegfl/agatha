package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.EventoRisco;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoRiscoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity EventoRisco and its DTO EventoRiscoDTO.
 */
@Mapper(componentModel = "spring", uses = {
        IdentificacaoMapper.class,
        EventoMapper.class,
        CategoriaMapper.class,
        NaturezaMapper.class,
        EventoCausaMapper.class,
        EventoConsequenciaMapper.class,
        ControleEventoMapper.class,
        CalculoRiscoMapper.class,
        PlanoControleMapper.class,
        RespostaRiscoMapper.class
})
public interface EventoRiscoMapper{

    @Mapping(target = "cpf", ignore = true)
    EventoRiscoDTO eventoRiscoToEventoRiscoDTO(EventoRisco eventoRisco);

    List<EventoRiscoDTO> eventoRiscosToEventoRiscoDTOs(List<EventoRisco> eventoRiscos);

    EventoRisco eventoRiscoDTOToEventoRisco(EventoRiscoDTO eventoRiscoDTO);

    List<EventoRisco> eventoRiscoDTOsToEventoRiscos(List<EventoRiscoDTO> eventoRiscoDTOs);

}