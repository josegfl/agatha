package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Calculadora and its DTO CalculadoraDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoCalculadoraMapper.class, ImpactoCalculadoraMapper.class, ProbabilidadeCalculadoraMapper.class})
public interface CalculadoraMapper{

    @Mapping(target = "processo", ignore = true)
    CalculadoraDTO calculadoraToCalculadoraDTO(Calculadora calculadora);

    List<CalculadoraDTO> calculadorasToCalculadoraDTOs(List<Calculadora> calculadoras);

    @Mapping(target = "processo", ignore = true)
    Calculadora calculadoraDTOToCalculadora(CalculadoraDTO calculadoraDTO);

    List<Calculadora> calculadoraDTOsToCalculadoras(List<CalculadoraDTO> calculadoraDTOs);
}
