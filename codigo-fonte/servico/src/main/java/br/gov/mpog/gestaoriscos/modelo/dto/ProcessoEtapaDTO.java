package br.gov.mpog.gestaoriscos.modelo.dto;

/**
 * Created by WGL004 on 10/11/2017.
 */
public class ProcessoEtapaDTO {

    private Boolean avaliacao;

    private Boolean resposta;

    private Boolean plano;

    private Boolean validacao;

    public Boolean getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Boolean avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Boolean getResposta() {
        return resposta;
    }

    public void setResposta(Boolean resposta) {
        this.resposta = resposta;
    }

    public Boolean getPlano() {
        return plano;
    }

    public void setPlano(Boolean plano) {
        this.plano = plano;
    }

    public Boolean getValidacao() {
        return validacao;
    }

    public void setValidacao(Boolean validacao) {
        this.validacao = validacao;
    }
}
