package br.gov.mpog.gestaoriscos.modelo.dto;

public class AvaliacaoDTO extends EtapaProcessoDTO {

    private Boolean ignorarRiscoInerente;

    public Boolean getIgnorarRiscoInerente() {
        return ignorarRiscoInerente;
    }

    public void setIgnorarRiscoInerente(Boolean ignorarRiscoInerente) {
        this.ignorarRiscoInerente = ignorarRiscoInerente;
    }

}