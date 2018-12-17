package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Glossario;
import br.gov.mpog.gestaoriscos.modelo.dto.GlossarioDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity Glossario and its DTO GlossarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GlossarioMapper{

    GlossarioDTO glossarioToGlossarioDTO(Glossario glossario);

    List<GlossarioDTO> glossariosToGlossarioDTOs(List<Glossario> glossarios);

    Glossario glossarioDTOToGlossario(GlossarioDTO glossarioDTO);

    List<Glossario> glossarioDTOsToGlossarios(List<GlossarioDTO> glossarioDTOs);

}