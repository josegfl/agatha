package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServicoDTO {
    private long codigoErro;
    private String mensagem;
    private String data;
    private String ipRequisitante;
    private String ticket;
    private String versaoServico;
}
