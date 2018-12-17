package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.TipoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoCalculadoraDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity TipoCalculadora and its DTO TipoCalculadoraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoCalculadoraMapper{

    TipoCalculadoraDTO tipoCalculadoraToTipoCalculadoraDTO(TipoCalculadora tipoCalculadora);

    List<TipoCalculadoraDTO> tipoCalculadorasToTipoCalculadoraDTOs(List<TipoCalculadora> tipoCalculadoras);

    TipoCalculadora tipoCalculadoraDTOToTipoCalculadora(TipoCalculadoraDTO tipoCalculadoraDTO);

    List<TipoCalculadora> tipoCalculadoraDTOsToTipoCalculadoras(List<TipoCalculadoraDTO> tipoCalculadoraDTOs);
}
