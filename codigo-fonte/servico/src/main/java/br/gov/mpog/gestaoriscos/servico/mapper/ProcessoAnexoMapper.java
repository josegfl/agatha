package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.ProcessoAnexo;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoAnexoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity ProcessoAnexo and its DTO ProcessoAnexoDTO.
 */
@Mapper(componentModel = "spring", uses = {ArquivoAnexoMapper.class})
public interface ProcessoAnexoMapper{

    ProcessoAnexoDTO processoAnexoToProcessoAnexoDTO(ProcessoAnexo processoAnexo);

    List<ProcessoAnexoDTO> processoAnexosToProcessoAnexoDTOs(List<ProcessoAnexo> processoAnexos);

    @Mapping(target = "processo", ignore = true)
    ProcessoAnexo processoAnexoDTOToProcessoAnexo(ProcessoAnexoDTO processoAnexoDTO);

    List<ProcessoAnexo> processoAnexoDTOsToProcessoAnexos(List<ProcessoAnexoDTO> processoAnexoDTOs);


}
