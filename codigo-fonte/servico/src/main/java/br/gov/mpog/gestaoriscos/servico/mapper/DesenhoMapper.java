package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Desenho;
import br.gov.mpog.gestaoriscos.modelo.dto.DesenhoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Desenho and its DTO DesenhoDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class})
public interface DesenhoMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "orgao", ignore = true)
    DesenhoDTO desenhoToDesenhoDTO(Desenho desenho);

    List<DesenhoDTO> desenhosToDesenhoDTOs(List<Desenho> desenhos);

    Desenho desenhoDTOToDesenho(DesenhoDTO desenhoDTO);

    List<Desenho> desenhoDTOsToDesenhos(List<DesenhoDTO> desenhoDTOs);

}