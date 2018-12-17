package br.gov.mpog.gestaoriscos.modelo.dto;

public class EntidadeBaseDescricaoStatusSearchDTO extends EntidadeBaseDTO {

    private String descricao;

    private String search;

    private boolean status;

    private String cpf;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
