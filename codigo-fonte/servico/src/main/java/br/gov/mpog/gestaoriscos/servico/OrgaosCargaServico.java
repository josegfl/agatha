package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.CargaStatusDTO;

public interface OrgaosCargaServico {

    CargaStatusDTO importarEstruturaOrganizacional() throws Exception;
}
