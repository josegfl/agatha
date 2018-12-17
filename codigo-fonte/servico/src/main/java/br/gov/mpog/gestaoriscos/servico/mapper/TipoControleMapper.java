package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.TipoControle;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoControleDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity TipoControle and its DTO TipoControleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoControleMapper{

    TipoControleDTO tipoControleToTipoControleDTO(TipoControle tipoControle);

    List<TipoControleDTO> tipoControlesToTipoControleDTOs(List<TipoControle> tipoControles);

    TipoControle tipoControleDTOToTipoControle(TipoControleDTO tipoControleDTO);

    List<TipoControle> tipoControleDTOsToTipoControles(List<TipoControleDTO> tipoControleDTOs);

}