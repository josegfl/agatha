package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Consequencia and its DTO ConsequenciaDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class})
public interface ConsequenciaMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "orgao", ignore = true)
    ConsequenciaDTO consequenciaToConsequenciaDTO(Consequencia consequencia);

    List<ConsequenciaDTO> consequenciasToConsequenciaDTOs(List<Consequencia> consequencias);

    Consequencia consequenciaDTOToConsequencia(ConsequenciaDTO consequenciaDTO);

    List<Consequencia> consequenciaDTOsToConsequencias(List<ConsequenciaDTO> consequenciaDTOs);
}