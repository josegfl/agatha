package br.gov.mpog.gestaoriscos.modelo.dto;

public class CargaStatusDTO {
    private int insercoes;
    private int atualizacoes;
    private String msg;
    private String tempoGasto;

    public CargaStatusDTO() {
    }

    public CargaStatusDTO(int inseridos, int atualizados, String msg, String tempoGasto) {
        this.insercoes = inseridos;
        this.atualizacoes = atualizados;
        this.msg = msg;
        this.tempoGasto = tempoGasto;
    }

    public int getInsercoes() {
        return insercoes;
    }

    public void setInsercoes(int insercoes) {
        this.insercoes = insercoes;
    }

    public int getAtualizacoes() {
        return atualizacoes;
    }

    public void setAtualizacoes(int atualizacoes) {
        this.atualizacoes = atualizacoes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(String tempoGasto) {
        this.tempoGasto = tempoGasto;
    }
}
