package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.List;

public class IdentificacaoDTO extends EtapaProcessoDTO {

    private List<EventoRiscoDTO> eventos;

    public List<EventoRiscoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoRiscoDTO> eventos) {
        this.eventos = eventos;
    }
}