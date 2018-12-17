package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.AgrupamentoTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.AgrupamentoTaxonomiaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity AgrupamentoTaxonomia and its DTO AgrupamentoTaxonomiaDTO.
 */
@Mapper(componentModel = "spring", uses = {TaxonomiaMapper.class})
public interface AgrupamentoTaxonomiaMapper{

    @Mapping(target = "cpf", ignore = true)
    AgrupamentoTaxonomiaDTO agrupamentoTaxonomiaToAgrupamentoTaxonomiaDTO(AgrupamentoTaxonomia agrupamentoTaxonomia);

    List<AgrupamentoTaxonomiaDTO> agrupamentoTaxonomiasToAgrupamentoTaxonomiaDTOs(List<AgrupamentoTaxonomia> agrupamentoTaxonomias);

    AgrupamentoTaxonomia agrupamentoTaxonomiaDTOToAgrupamentoTaxonomia(AgrupamentoTaxonomiaDTO agrupamentoTaxonomiaDTO);

    List<AgrupamentoTaxonomia> agrupamentoTaxonomiaDTOsToAgrupamentoTaxonomias(List<AgrupamentoTaxonomiaDTO> agrupamentoTaxonomiaDTOs);

}