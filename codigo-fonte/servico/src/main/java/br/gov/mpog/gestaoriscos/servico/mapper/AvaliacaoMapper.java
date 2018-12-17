package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Avaliacao;
import br.gov.mpog.gestaoriscos.modelo.dto.AvaliacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Avaliacao and its DTO AvaliacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProcessoMapper.class})
public interface AvaliacaoMapper {

    @Mapping(target = "processo", ignore = true)
    AvaliacaoDTO avaliacaoToAvaliacaoDTO(Avaliacao avaliacao);

    List<AvaliacaoDTO> avaliacaosToAvaliacaoDTOs(List<Avaliacao> avaliacaos);

    @Mapping(target = "processo", ignore = true)
    Avaliacao avaliacaoDTOToAvaliacao(AvaliacaoDTO avaliacaoDTO);

    List<Avaliacao> avaliacaoDTOsToAvaliacaos(List<AvaliacaoDTO> avaliacaoDTOs);
}