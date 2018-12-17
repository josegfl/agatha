package br.gov.mpog.gestaoriscos.modelo.dto;

public class ProbabilidadeCalculadoraDTO extends EntidadeBaseItemCalculadoraDTO {

    private Long frequencia;

    public Long getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Long frequencia) {
        this.frequencia = frequencia;
    }
}
