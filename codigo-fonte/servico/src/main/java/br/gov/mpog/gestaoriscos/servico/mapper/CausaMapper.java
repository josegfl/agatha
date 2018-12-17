package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Causa and its DTO CausaDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class})
public interface CausaMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "orgao", ignore = true)
    CausaDTO causaToCausaDTO(Causa causa);

    List<CausaDTO> causasToCausaDTOs(List<Causa> causas);

    Causa causaDTOToCausa(CausaDTO causaDTO);

    List<Causa> causaDTOsToCausas(List<CausaDTO> causaDTOs);

}