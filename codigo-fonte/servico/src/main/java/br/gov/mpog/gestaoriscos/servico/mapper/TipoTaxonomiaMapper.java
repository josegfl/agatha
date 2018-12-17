package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.TipoTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity TipoTaxonomia and its DTO TipoTaxonomiaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoTaxonomiaMapper{

    TipoTaxonomiaDTO tipoTaxonomiaToTipoTaxonomiaDTO(TipoTaxonomia tipoTaxonomia);

    List<TipoTaxonomiaDTO> tipoTaxonomiasToTipoTaxonomiaDTOs(List<TipoTaxonomia> tipoTaxonomias);

    TipoTaxonomia tipoTaxonomiaDTOToTipoTaxonomia(TipoTaxonomiaDTO tipoTaxonomiaDTO);

    List<TipoTaxonomia> tipoTaxonomiaDTOsToTipoTaxonomias(List<TipoTaxonomiaDTO> tipoTaxonomiaDTOs);

}