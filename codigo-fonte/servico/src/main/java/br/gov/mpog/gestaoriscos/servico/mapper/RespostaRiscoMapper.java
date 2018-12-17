package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.RespostaRisco;
import br.gov.mpog.gestaoriscos.modelo.dto.RespostaRiscoDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity RespostaRisco and its DTO RespostaRiscoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RespostaRiscoMapper {

    RespostaRiscoDTO respostaRiscoToRespostaRiscoDTO(RespostaRisco respostaRisco);

    List<RespostaRiscoDTO> respostaRiscosToRespostaRiscoDTOs(List<RespostaRisco> respostaRiscos);

    RespostaRisco respostaRiscoDTOToRespostaRisco(RespostaRiscoDTO respostaRiscoDTO);

    List<RespostaRisco> respostaRiscoDTOsToRespostaRiscos(List<RespostaRiscoDTO> respostaRiscoDTOS);

}