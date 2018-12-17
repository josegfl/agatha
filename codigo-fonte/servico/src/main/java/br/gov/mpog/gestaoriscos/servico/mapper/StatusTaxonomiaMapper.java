package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.StatusTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.StatusTaxonomiaDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity StatusTaxonomia and its DTO StatusTaxonomiaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatusTaxonomiaMapper{

    StatusTaxonomiaDTO statusTaxonomiaToStatusTaxonomiaDTO(StatusTaxonomia statusTaxonomia);

    List<StatusTaxonomiaDTO> statusTaxonomiasToStatusTaxonomiaDTOs(List<StatusTaxonomia> statusTaxonomias);

    StatusTaxonomia statusTaxonomiaDTOToStatusTaxonomia(StatusTaxonomiaDTO statusTaxonomiaDTO);

    List<StatusTaxonomia> statusTaxonomiaDTOsToStatusTaxonomias(List<StatusTaxonomiaDTO> statusTaxonomiaDTOs);

}