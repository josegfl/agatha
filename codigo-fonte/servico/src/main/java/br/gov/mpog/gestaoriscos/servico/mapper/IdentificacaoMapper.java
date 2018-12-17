package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Identificacao;
import br.gov.mpog.gestaoriscos.modelo.dto.IdentificacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Identificacao and its DTO IdentificacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProcessoMapper.class})
public interface IdentificacaoMapper {

    @Mapping(target = "processo", ignore = true)
    @Mapping(target = "eventos", ignore = true)
    IdentificacaoDTO identificacaoToIdentificacaoDTO(Identificacao identificacao);

    List<IdentificacaoDTO> identificacaosToIdentificacaoDTOs(List<Identificacao> identificacaos);

    @Mapping(target = "processo", ignore = true)
    @Mapping(target = "eventos", ignore = true)
    Identificacao identificacaoDTOToIdentificacao(IdentificacaoDTO identificacaoDTO);

    List<Identificacao> identificacaoDTOsToIdentificacaos(List<IdentificacaoDTO> identificacaoDTOs);
}
