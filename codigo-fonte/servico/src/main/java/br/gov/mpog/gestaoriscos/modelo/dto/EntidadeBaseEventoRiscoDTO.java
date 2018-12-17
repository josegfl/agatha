package br.gov.mpog.gestaoriscos.modelo.dto;

public class EntidadeBaseEventoRiscoDTO extends EntidadeBaseDTO {

    private String cpf;

    private EventoRiscoDTO eventoRisco;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public EventoRiscoDTO getEventoRisco() {
        return eventoRisco;
    }

    public void setEventoRisco(EventoRiscoDTO eventoRisco) {
        this.eventoRisco = eventoRisco;
    }

}