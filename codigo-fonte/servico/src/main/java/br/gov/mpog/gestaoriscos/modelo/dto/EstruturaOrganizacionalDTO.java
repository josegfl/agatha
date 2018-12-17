package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EstruturaOrganizacionalDTO {
    private ServicoDTO servico;
    private List<OrgaoCargaDTO> unidades;
}
