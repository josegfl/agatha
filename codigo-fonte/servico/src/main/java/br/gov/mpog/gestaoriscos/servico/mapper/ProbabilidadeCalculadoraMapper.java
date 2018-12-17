package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.ProbabilidadeCalculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.ProbabilidadeCalculadoraDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ProbabilidadeCalculadora and its DTO ProbabilidadeCalculadoraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProbabilidadeCalculadoraMapper{

    @Mapping(target = "calculadora", ignore = true)
    ProbabilidadeCalculadoraDTO probabilidadeCalculadoraToProbabilidadeCalculadoraDTO(ProbabilidadeCalculadora probabilidadeCalculadora);

    List<ProbabilidadeCalculadoraDTO> probabilidadeCalculadorasToProbabilidadeCalculadoraDTOs(List<ProbabilidadeCalculadora> probabilidadeCalculadoras);

    @Mapping(target = "calculadora", ignore = true)
    ProbabilidadeCalculadora probabilidadeCalculadoraDTOToProbabilidadeCalculadora(ProbabilidadeCalculadoraDTO probabilidadeCalculadoraDTO);

    List<ProbabilidadeCalculadora> probabilidadeCalculadoraDTOsToProbabilidadeCalculadoras(List<ProbabilidadeCalculadoraDTO> probabilidadeCalculadoraDTOs);
}
