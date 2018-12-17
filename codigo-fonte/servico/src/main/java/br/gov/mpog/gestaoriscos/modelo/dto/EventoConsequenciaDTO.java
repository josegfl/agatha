package br.gov.mpog.gestaoriscos.modelo.dto;

public class EventoConsequenciaDTO extends EntidadeBaseEventoRiscoDTO {

    private ConsequenciaDTO consequencia;

    public ConsequenciaDTO getConsequencia() {
        return consequencia;
    }

    public void setConsequencia(ConsequenciaDTO consequencia) {
        this.consequencia = consequencia;
    }
}