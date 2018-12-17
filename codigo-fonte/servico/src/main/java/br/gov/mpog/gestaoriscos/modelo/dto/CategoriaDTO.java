package br.gov.mpog.gestaoriscos.modelo.dto;

public class CategoriaDTO extends EntidadeBaseDescricaoStatusSearchDTO {

    private NaturezaDTO natureza;

    public NaturezaDTO getNatureza() {
        return natureza;
    }

    public void setNatureza(NaturezaDTO natureza) {
        this.natureza = natureza;
    }
}