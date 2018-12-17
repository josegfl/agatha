package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Orgao and its DTO OrgaoDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoriaUnidadeMapper.class, NaturezaJuridicaMapper.class, UsuarioMapper.class})
public interface OrgaoMapper{

    @Mapping(target = "orgaoPai", ignore = true)
    @Mapping(target = "orgaosFilhos", ignore = true)
    OrgaoDTO orgaoToOrgaoDTO(Orgao orgao);

    List<OrgaoDTO> orgaosToOrgaoDTOs(List<Orgao> orgaos);

    Orgao orgaoDTOToOrgao(OrgaoDTO orgaoDTO);

    List<Orgao> orgaoDTOsToOrgaos(List<OrgaoDTO> orgaoDTOs);

}
