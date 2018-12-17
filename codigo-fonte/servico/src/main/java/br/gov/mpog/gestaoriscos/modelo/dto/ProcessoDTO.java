package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.base.ProcessoBase;

import java.util.List;

public class ProcessoDTO extends ProcessoBase {

    private StatusProcessoDTO status;

    private MacroprocessoDTO macroprocesso;

    private UsuarioDTO gestor;

    private List<ResponsavelDTO> responsaveis;

    private List<ProcessoAnexoDTO> anexos;

    private AnaliseDTO analise;

    private IdentificacaoDTO identificacao;

    private AvaliacaoDTO avaliacao;

    private List<CalculadoraDTO> calculadoras;

    private DecisaoProcessoDTO decisao;

    public StatusProcessoDTO getStatus() {
        return status;
    }

    public MacroprocessoDTO getMacroprocesso() {
        return macroprocesso;
    }

    public void setMacroprocesso(MacroprocessoDTO macroprocesso) {
        this.macroprocesso = macroprocesso;
    }

    public void setStatus(StatusProcessoDTO status) {
        this.status = status;
    }

    public UsuarioDTO getGestor() {
        return gestor;
    }

    public void setGestor(UsuarioDTO gestor) {
        this.gestor = gestor;
    }

    public List<ResponsavelDTO> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<ResponsavelDTO> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public List<ProcessoAnexoDTO> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ProcessoAnexoDTO> anexos) {
        this.anexos = anexos;
    }

    public AnaliseDTO getAnalise() {
        return analise;
    }

    public void setAnalise(AnaliseDTO analise) {
        this.analise = analise;
    }

    public IdentificacaoDTO getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(IdentificacaoDTO identificacao) {
        this.identificacao = identificacao;
    }

    public AvaliacaoDTO getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoDTO avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<CalculadoraDTO> getCalculadoras() {
        return calculadoras;
    }

    public void setCalculadoras(List<CalculadoraDTO> calculadoras) {
        this.calculadoras = calculadoras;
    }

    public DecisaoProcessoDTO getDecisao() {
        return decisao;
    }

    public void setDecisao(DecisaoProcessoDTO decisao) {
        this.decisao = decisao;
    }
}