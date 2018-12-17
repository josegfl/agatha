package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.CalculoRisco;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculoRiscoDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CalculoRisco and its DTO CalculoRiscoDTO.
 */
@Mapper(componentModel = "spring", uses = {PesoEventoRiscoMapper.class})
public interface CalculoRiscoMapper{

    CalculoRiscoDTO calculoRiscoToCalculoRiscoDTO(CalculoRisco calculoRisco);

    List<CalculoRiscoDTO> calculoRiscosToCalculoRiscoDTOs(List<CalculoRisco> calculoRiscos);

    CalculoRisco calculoRiscoDTOToCalculoRisco(CalculoRiscoDTO calculoRiscoDTO);

    List<CalculoRisco> calculoRiscoDTOsToCalculoRiscos(List<CalculoRiscoDTO> calculoRiscoDTOs);
}
