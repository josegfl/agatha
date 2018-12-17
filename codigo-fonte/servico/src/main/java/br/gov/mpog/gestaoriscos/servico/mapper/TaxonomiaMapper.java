package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Taxonomia and its DTO TaxonomiaDTO.
 */
@Mapper(componentModel = "spring", uses = {
        TipoTaxonomiaMapper.class,
        StatusTaxonomiaMapper.class,
        OrgaoMapper.class,
        EventoMapper.class,
        CausaMapper.class,
        ConsequenciaMapper.class,
        ControleMapper.class,
        AgrupamentoTaxonomiaMapper.class
})
public interface TaxonomiaMapper{

    @Mapping(target = "agrupamento", ignore = true)
    TaxonomiaDTO taxonomiaToTaxonomiaDTO(Taxonomia taxonomia);

    List<TaxonomiaDTO> taxonomiasToTaxonomiaDTOs(List<Taxonomia> taxonomias);

    @Mapping(target = "agrupamento", ignore = true)
    Taxonomia taxonomiaDTOToTaxonomia(TaxonomiaDTO taxonomiaDTO);

    List<Taxonomia> taxonomiaDTOsToTaxonomias(List<TaxonomiaDTO> taxonomiaDTOs);

}