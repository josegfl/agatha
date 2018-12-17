package br.gov.mpog.gestaoriscos.modelo.dto;

public class EntidadeBaseItemCalculadoraDTO extends EntidadeBaseNomeDTO {

    private CalculadoraDTO calculadora;

    public CalculadoraDTO getCalculadora() {
        return calculadora;
    }

    public void setCalculadora(CalculadoraDTO calculadora) {
        this.calculadora = calculadora;
    }
}