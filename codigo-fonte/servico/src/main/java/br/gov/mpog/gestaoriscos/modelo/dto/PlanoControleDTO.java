package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.Calendar;

public class PlanoControleDTO extends EntidadeBaseEventoRiscoDTO {

    private ControleDTO controle;

    private TipoControleDTO tipoControle;

    private ObjetivoControleDTO objetivo;

    private String areaResponsavel;

    private String responsavel;

    private String interveniente;

    private String implementacao;

    private Calendar dtInicio;

    private Calendar dtConclusao;

    public ControleDTO getControle() {
        return controle;
    }

    public void setControle(ControleDTO controle) {
        this.controle = controle;
    }

    public TipoControleDTO getTipoControle() {
        return tipoControle;
    }

    public void setTipoControle(TipoControleDTO tipoControle) {
        this.tipoControle = tipoControle;
    }

    public ObjetivoControleDTO getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ObjetivoControleDTO objetivo) {
        this.objetivo = objetivo;
    }

    public String getAreaResponsavel() {
        return areaResponsavel;
    }

    public void setAreaResponsavel(String areaResponsavel) {
        this.areaResponsavel = areaResponsavel;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getInterveniente() {
        return interveniente;
    }

    public void setInterveniente(String interveniente) {
        this.interveniente = interveniente;
    }

    public String getImplementacao() {
        return implementacao;
    }

    public void setImplementacao(String implementacao) {
        this.implementacao = implementacao;
    }

    public Calendar getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Calendar dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Calendar getDtConclusao() {
        return dtConclusao;
    }

    public void setDtConclusao(Calendar dtConclusao) {
        this.dtConclusao = dtConclusao;
    }

}