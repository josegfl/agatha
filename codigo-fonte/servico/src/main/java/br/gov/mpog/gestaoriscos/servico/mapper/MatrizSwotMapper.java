package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.MatrizSwot;
import br.gov.mpog.gestaoriscos.modelo.dto.MatrizSwotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity MatrizSwot and its DTO MatrizSwotDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoMatrizMapper.class, AnaliseMapper.class})
public interface MatrizSwotMapper{

    MatrizSwotDTO matrizSwotToMatrizSwotDTO(MatrizSwot matrizSwot);

    List<MatrizSwotDTO> matrizSwotsToMatrizSwotDTOs(List<MatrizSwot> matrizSwots);

    @Mapping(target = "analise", ignore = true)
    MatrizSwot matrizSwotDTOToMatrizSwot(MatrizSwotDTO matrizSwotDTO);

    List<MatrizSwot> matrizSwotDTOsToMatrizSwots(List<MatrizSwotDTO> matrizSwotDTOs);
}
