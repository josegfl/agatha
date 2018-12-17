package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Natureza;
import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Natureza and its DTO NaturezaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NaturezaMapper{

    @Mapping(target = "cpf", ignore = true)
    NaturezaDTO naturezaToNaturezaDTO(Natureza natureza);

    List<NaturezaDTO> naturezasToNaturezaDTOs(List<Natureza> naturezas);

    Natureza naturezaDTOToNatureza(NaturezaDTO naturezaDTO);

    List<Natureza> naturezaDTOsToNaturezas(List<NaturezaDTO> naturezaDTOs);


}
