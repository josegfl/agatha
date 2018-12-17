package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrgaoConsultaDTO {

    private Long idOrgaoPai;

    private List<Long> listaIdCategorias;

}
