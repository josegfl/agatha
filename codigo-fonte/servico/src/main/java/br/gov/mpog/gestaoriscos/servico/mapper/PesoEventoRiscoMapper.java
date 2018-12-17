package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.PesoEventoRisco;
import br.gov.mpog.gestaoriscos.modelo.dto.PesoEventoRiscoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity PesoEventoRisco and its DTO PesoEventoRiscoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PesoEventoRiscoMapper{

    @Mapping(target = "calculoRisco", ignore = true)
    PesoEventoRiscoDTO pesoEventoRiscoToPesoEventoRiscoDTO(PesoEventoRisco pesoEventoRisco);

    List<PesoEventoRiscoDTO> pesoEventoRiscosToPesoEventoRiscoDTOs(List<PesoEventoRisco> pesoEventoRiscos);

    @Mapping(target = "calculoRisco", ignore = true)
    PesoEventoRisco pesoEventoRiscoDTOToPesoEventoRisco(PesoEventoRiscoDTO pesoEventoRiscoDTO);

    List<PesoEventoRisco> pesoEventoRiscoDTOsToPesoEventoRiscos(List<PesoEventoRiscoDTO> pesoEventoRiscoDTOs);
}
