package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtoNormativoDTO {
    private String tipoAto;
    private int codigoUnidade;
    private String numero;
    private String dataAssinatura;
    private String dataPublicacao;
    private String ementa;
    private String url;
}
