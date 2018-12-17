package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.TipoMatriz;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoMatrizDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity TipoMatriz and its DTO TipoMatrizDTO.
 */
@Mapper(componentModel = "spring", uses = {MatrizSwotMapper.class})
public interface TipoMatrizMapper{

    TipoMatrizDTO tipoMatrizToTipoMatrizDTO(TipoMatriz tipoMatriz);

    List<TipoMatrizDTO> tipoMatrizsToTipoMatrizDTOs(List<TipoMatriz> tipoMatrizs);

    @Mapping(target = "matrizes", ignore = true)
    TipoMatriz tipoMatrizDTOToTipoMatriz(TipoMatrizDTO tipoMatrizDTO);

    List<TipoMatriz> tipoMatrizDTOsToTipoMatrizs(List<TipoMatrizDTO> tipoMatrizDTOs);
}
