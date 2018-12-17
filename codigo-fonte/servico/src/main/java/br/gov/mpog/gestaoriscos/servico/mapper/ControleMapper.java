package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Controle and its DTO ControleDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class})
public interface ControleMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "orgao", ignore = true)
    ControleDTO controleToControleDTO(Controle controle);

    List<ControleDTO> controlesToControleDTOs(List<Controle> controles);

    Controle controleDTOToControle(ControleDTO controleDTO);

    List<Controle> controleDTOsToControles(List<ControleDTO> controleDTOs);

}