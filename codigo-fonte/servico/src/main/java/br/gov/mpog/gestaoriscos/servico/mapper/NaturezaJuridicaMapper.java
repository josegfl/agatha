package br.gov.mpog.gestaoriscos.servico.mapper;

import br.gov.mpog.gestaoriscos.modelo.NaturezaJuridica;
import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaJuridicaDTO;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Mapper for the entity Orgao and its DTO OrgaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NaturezaJuridicaMapper {

    NaturezaJuridicaDTO naturezaJuridicaToNaturezaJuridicaDTO(NaturezaJuridica naturezaJuridica);

    List<NaturezaJuridicaDTO> naturezaJuridicasToNaturezaJuridicaDTOs(List<NaturezaJuridica> naturezaJuridicas);

    NaturezaJuridica naturezaJuridicaDTOToNaturezaJuridica(NaturezaJuridicaDTO naturezaJuridicaDTO);

    List<NaturezaJuridica> naturezaJuridicaDTOsToNaturezaJuridicas(List<NaturezaJuridicaDTO> naturezaJuridicaDTOs);

}
