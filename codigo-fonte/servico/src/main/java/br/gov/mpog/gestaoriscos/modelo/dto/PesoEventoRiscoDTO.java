package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.base.PesoEventoRiscoBase;

public class PesoEventoRiscoDTO extends PesoEventoRiscoBase {

    private CalculoRiscoDTO calculoRisco;

    public CalculoRiscoDTO getCalculoRisco() {
        return calculoRisco;
    }

    public void setCalculoRisco(CalculoRiscoDTO calculoRisco) {
        this.calculoRisco = calculoRisco;
    }
}
