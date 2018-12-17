package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.CategoriaUnidade;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaUnidadeDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity Orgao and its DTO OrgaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaUnidadeMapper {

    CategoriaUnidadeDTO categoriaUnidadeToCategoriaUnidadeDTO(CategoriaUnidade categoriaUnidade);

    List<CategoriaUnidadeDTO> categoriaUnidadesToCategoriaUnidadeDTOs(List<CategoriaUnidade> categoriaUnidades);

    CategoriaUnidade categoriaUnidadeDTOToCategoriaUnidade(CategoriaUnidadeDTO categoriaUnidadeDTO);

    List<CategoriaUnidade> categoriaUnidadeDTOsToCategoriaUnidades(List<CategoriaUnidadeDTO> categoriaUnidadeDTOs);

}
