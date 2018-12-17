package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Operacao;
import br.gov.mpog.gestaoriscos.modelo.dto.OperacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Operacao and its DTO OperacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {OrgaoMapper.class})
public interface OperacaoMapper{

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "orgao", ignore = true)
    OperacaoDTO operacaoToOperacaoDTO(Operacao operacao);

    List<OperacaoDTO> operacaosToOperacaoDTOs(List<Operacao> operacaos);

    Operacao operacaoDTOToOperacao(OperacaoDTO operacaoDTO);

    List<Operacao> operacaoDTOsToOperacaos(List<OperacaoDTO> operacaoDTOs);

}