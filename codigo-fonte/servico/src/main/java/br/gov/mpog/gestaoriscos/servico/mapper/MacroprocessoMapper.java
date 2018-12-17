package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Macroprocesso and its DTO MacroprocessoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MacroprocessoMapper {

    MacroprocessoDTO macroprocessoToMacroprocessoDTO(Macroprocesso macroprocesso);

    List<MacroprocessoDTO> macroprocessosToMacroprocessoDTOs(List<Macroprocesso> macroprocessos);

    Macroprocesso macroprocessoDTOToMacroprocesso(MacroprocessoDTO macroprocessoDTO);

    List<Macroprocesso> macroprocessoDTOsToMacroprocessos(List<MacroprocessoDTO> macroprocessoDTOs);

}
