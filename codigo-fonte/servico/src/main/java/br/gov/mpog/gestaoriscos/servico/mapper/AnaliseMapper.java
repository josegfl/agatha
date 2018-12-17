package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Analise;
import br.gov.mpog.gestaoriscos.modelo.dto.AnaliseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Analise and its DTO AnaliseDTO.
 */
@Mapper(componentModel = "spring", uses = {MatrizSwotMapper.class, ProcessoMapper.class, OrgaoMapper.class})
public interface AnaliseMapper {

    @Mapping(target = "processo", ignore = true)
    AnaliseDTO analiseToAnaliseDTO(Analise analise);

    List<AnaliseDTO> analisesToAnaliseDTOs(List<Analise> analises);

    @Mapping(target = "processo", ignore = true)
    Analise analiseDTOToAnalise(AnaliseDTO analiseDTO);

    List<Analise> analiseDTOsToAnalises(List<AnaliseDTO> analiseDTOs);
}
