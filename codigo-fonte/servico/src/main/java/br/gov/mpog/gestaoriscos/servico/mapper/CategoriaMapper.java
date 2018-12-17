package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.Categoria;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {NaturezaMapper.class})
public interface CategoriaMapper{

    @Mapping(target = "cpf", ignore = true)
    CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);

    List<CategoriaDTO> categoriasToCategoriaDTOs(List<Categoria> categorias);

    Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO);

    List<Categoria> categoriaDTOsToCategorias(List<CategoriaDTO> categoriaDTOs);


}
