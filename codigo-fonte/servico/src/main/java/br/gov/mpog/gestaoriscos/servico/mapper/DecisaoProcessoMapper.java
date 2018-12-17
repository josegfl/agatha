package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.DecisaoProcesso;
import br.gov.mpog.gestaoriscos.modelo.dto.DecisaoProcessoDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity DecisaoProcesso and its DTO DecisaoProcessoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DecisaoProcessoMapper {

    DecisaoProcessoDTO decisaoProcessoToDecisaoProcessoDTO(DecisaoProcesso decisaoProcesso);

    List<DecisaoProcessoDTO> decisaoProcessosToDecisaoProcessoDTOs(List<DecisaoProcesso> decisaoProcessos);

    DecisaoProcesso decisaoProcessoDTOToDecisaoProcesso(DecisaoProcessoDTO decisaoProcessoDTO);

    List<DecisaoProcesso> decisaoProcessoDTOsToDecisaoProcessos(List<DecisaoProcessoDTO> decisaoProcessoDTOs);
}
