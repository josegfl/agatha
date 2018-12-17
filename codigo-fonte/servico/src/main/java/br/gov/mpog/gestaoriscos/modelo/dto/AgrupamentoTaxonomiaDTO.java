package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.List;

public class AgrupamentoTaxonomiaDTO{

    private Long id;

    private TaxonomiaDTO taxonomia;

    private boolean excluido;

    private List<TaxonomiaDTO> taxonomias;

    private String cpf;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public TaxonomiaDTO getTaxonomia(){
        return taxonomia;
    }

    public void setTaxonomia(TaxonomiaDTO taxonomia){
        this.taxonomia = taxonomia;
    }

    public boolean isExcluido(){
        return excluido;
    }

    public void setExcluido(boolean excluido){
        this.excluido = excluido;
    }

    public List<TaxonomiaDTO> getTaxonomias(){
        return taxonomias;
    }

    public void setTaxonomias(List<TaxonomiaDTO> taxonomias){
        this.taxonomias = taxonomias;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }
}