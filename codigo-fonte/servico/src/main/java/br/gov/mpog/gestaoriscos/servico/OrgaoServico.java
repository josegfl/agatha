package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoConsultaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;

import java.util.List;

public interface OrgaoServico {

    List<OrgaoDTO> listarOrgaos(final Long id);

    List<OrgaoDTO> filtrar(String nomeOrgao);

    List<OrgaoDTO> getUnidadePeloIdLimitadoPorIdCategoria(OrgaoConsultaDTO orgaoConsultaDTO);

}
