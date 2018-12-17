package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.List;

public class TaxonomiaContainerDTO{
    private List<TaxonomiaDTO> taxonomias;

    private String justificativa;

    public List<TaxonomiaDTO> getTaxonomias(){
        return taxonomias;
    }

    public void setTaxonomias(List<TaxonomiaDTO> taxonomias){
        this.taxonomias = taxonomias;
    }

    public String getJustificativa(){
        return justificativa;
    }

    public void setJustificativa(String justificativa){
        this.justificativa = justificativa;
    }
}