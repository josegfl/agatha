package br.gov.mpog.gestaoriscos.modelo.dto;

public class EntidadeBaseDTO {

    private Long id;

    private boolean excluido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }
}
