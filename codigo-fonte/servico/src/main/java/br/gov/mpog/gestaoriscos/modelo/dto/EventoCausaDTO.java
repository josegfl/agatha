package br.gov.mpog.gestaoriscos.modelo.dto;

public class EventoCausaDTO extends EntidadeBaseEventoRiscoDTO {

    private CausaDTO causa;

    public CausaDTO getCausa() {
        return causa;
    }

    public void setCausa(CausaDTO causa) {
        this.causa = causa;
    }
}