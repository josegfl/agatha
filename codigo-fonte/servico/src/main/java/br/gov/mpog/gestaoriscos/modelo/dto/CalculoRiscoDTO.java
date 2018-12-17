package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.base.CalculoRiscoBase;

import java.util.List;

public class CalculoRiscoDTO extends CalculoRiscoBase {

    private List<PesoEventoRiscoDTO> pesos;

    public List<PesoEventoRiscoDTO> getPesos() {
        return pesos;
    }

    public void setPesos(List<PesoEventoRiscoDTO> pesos) {
        this.pesos = pesos;
    }
}