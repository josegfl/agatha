
package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Processo and its DTO ProcessoDTO.
 */
@Mapper(componentModel = "spring", uses = {
        AnaliseMapper.class,
        IdentificacaoMapper.class,
        AvaliacaoMapper.class,
        ResponsavelMapper.class,
        UsuarioMapper.class,
        StatusProcessoMapper.class,
        ArquivoAnexoMapper.class,
        CalculadoraMapper.class,
        ProcessoAnexoMapper.class,
        MacroprocessoMapper.class,
        DecisaoProcessoMapper.class
})
public interface ProcessoMapper {

    ProcessoDTO processoToProcessoDTO(Processo processo);

    List<ProcessoDTO> processosToProcessoDTOs(List<Processo> processos);

    Processo processoDTOToProcesso(ProcessoDTO processoDTO);

    List<Processo> processoDTOsToProcessos(List<ProcessoDTO> processoDTOs);
}
