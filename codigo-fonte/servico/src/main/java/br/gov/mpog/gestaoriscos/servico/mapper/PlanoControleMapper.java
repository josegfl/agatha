package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.dto.PlanoControleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper for the entity PlanoControle and its DTO PlanoControleDTO.
 */
@Mapper(componentModel = "spring", uses = {ControleMapper.class, TipoControleMapper.class, ObjetivoControleMapper.class, EventoRiscoMapper.class})
public interface PlanoControleMapper {

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "eventoRisco", ignore = true)
    PlanoControleDTO planoControleToPlanoControleDTO(PlanoControle planoControle);

    List<PlanoControleDTO> planoControlesToPlanoControleDTOs(List<PlanoControle> planoControles);

    PlanoControle planoControleDTOToPlanoControle(PlanoControleDTO planoControleDTO);

    List<PlanoControle> planoControleDTOsToPlanoControles(List<PlanoControleDTO> planoControleDTOS);

}