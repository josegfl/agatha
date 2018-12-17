package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleEventoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ControleEvento and its DTO ControleEventoDTO.
 */
@Mapper(componentModel = "spring", uses = {ControleMapper.class, DesenhoMapper.class, OperacaoMapper.class})
public interface ControleEventoMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "eventoRisco", ignore = true)
    ControleEventoDTO controleEventoToControleEventoDTO(ControleEvento controleEvento);

    List<ControleEventoDTO> controleEventosToControleEventoDTOs(List<ControleEvento> controleEventos);

    @Mapping(target = "eventoRisco", ignore = true)
    ControleEvento controleEventoDTOToControleEvento(ControleEventoDTO controleEventoDTO);

    List<ControleEvento> controleEventoDTOsToControleEventos(List<ControleEventoDTO> controleEventoDTOs);
}