package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.ArquivoAnexo;
import br.gov.mpog.gestaoriscos.modelo.dto.ArquivoAnexoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity ArquivoAnexo and its DTO ArquivoAnexoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArquivoAnexoMapper{

    ArquivoAnexoDTO arquivoAnexoToArquivoAnexoDTO(ArquivoAnexo arquivoAnexo);

    List<ArquivoAnexoDTO> arquivoAnexosToArquivoAnexoDTOs(List<ArquivoAnexo> arquivoAnexos);

    @Mapping(target = "arquivo", ignore = true)
    ArquivoAnexo arquivoAnexoDTOToArquivoAnexo(ArquivoAnexoDTO arquivoAnexoDTO);

    List<ArquivoAnexo> arquivoAnexoDTOsToArquivoAnexos(List<ArquivoAnexoDTO> arquivoAnexoDTOs);
}
