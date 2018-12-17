package br.gov.mpog.gestaoriscos.modelo.dto;

public class ImpactoCalculadoraDTO extends EntidadeBaseItemCalculadoraDTO {

    private Long peso;

    private Boolean desabilitado;

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Boolean getDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(Boolean desabilitado) {
        this.desabilitado = desabilitado;
    }
}
