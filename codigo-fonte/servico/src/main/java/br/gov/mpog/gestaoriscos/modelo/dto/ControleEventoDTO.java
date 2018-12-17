package br.gov.mpog.gestaoriscos.modelo.dto;

public class ControleEventoDTO extends EntidadeBaseEventoRiscoDTO {

    private ControleDTO controle;

    private DesenhoDTO desenho;

    private OperacaoDTO operacao;

    public ControleDTO getControle() {
        return controle;
    }

    public void setControle(ControleDTO controle) {
        this.controle = controle;
    }

    public DesenhoDTO getDesenho() {
        return desenho;
    }

    public void setDesenho(DesenhoDTO desenho) {
        this.desenho = desenho;
    }

    public OperacaoDTO getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoDTO operacao) {
        this.operacao = operacao;
    }
}