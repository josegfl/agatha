package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.ObjetivoControle;
import br.gov.mpog.gestaoriscos.modelo.dto.ObjetivoControleDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity ObjetivoControle and its DTO ObjetivoControleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ObjetivoControleMapper{

    ObjetivoControleDTO objetivoControleToObjetivoControleDTO(ObjetivoControle objetivoControle);

    List<ObjetivoControleDTO> objetivoControlesToObjetivoControleDTOs(List<ObjetivoControle> objetivoControles);

    ObjetivoControle objetivoControleDTOToObjetivoControle(ObjetivoControleDTO objetivoControleDTO);

    List<ObjetivoControle> objetivoControleDTOsToObjetivoControles(List<ObjetivoControleDTO> objetivoControleDTOs);

}