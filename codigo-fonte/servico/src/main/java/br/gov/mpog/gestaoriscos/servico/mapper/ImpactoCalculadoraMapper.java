package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.ImpactoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.ImpactoCalculadoraDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ImpactoCalculadora and its DTO ImpactoCalculadoraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImpactoCalculadoraMapper{

    @Mapping(target = "calculadora", ignore = true)
    ImpactoCalculadoraDTO impactoCalculadoraToImpactoCalculadoraDTO(ImpactoCalculadora impactoCalculadora);

    List<ImpactoCalculadoraDTO> impactoCalculadorasToImpactoCalculadoraDTOs(List<ImpactoCalculadora> impactoCalculadoras);

    @Mapping(target = "calculadora", ignore = true)
    ImpactoCalculadora impactoCalculadoraDTOToImpactoCalculadora(ImpactoCalculadoraDTO impactoCalculadoraDTO);

    List<ImpactoCalculadora> impactoCalculadoraDTOsToImpactoCalculadoras(List<ImpactoCalculadoraDTO> impactoCalculadoraDTOs);
}
