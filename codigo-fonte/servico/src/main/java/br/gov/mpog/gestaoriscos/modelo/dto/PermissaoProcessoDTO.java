package br.gov.mpog.gestaoriscos.modelo.dto;

public class PermissaoProcessoDTO{

    private Boolean criar;
    private Boolean validar;
    private Boolean excluir;
    private Boolean enviarParaValidacao;
    private Boolean criarEventoRisco;
    private Boolean consultarUnidade;

    private OrgaoDTO orgao;

    public Boolean getCriar(){
        return criar;
    }

    public void setCriar(Boolean criar){
        this.criar = criar;
    }

    public Boolean getValidar(){
        return validar;
    }

    public void setValidar(Boolean validar){
        this.validar = validar;
    }

    public Boolean getExcluir(){
        return excluir;
    }

    public void setExcluir(Boolean excluir){
        this.excluir = excluir;
    }

    public Boolean getEnviarParaValidacao(){
        return enviarParaValidacao;
    }

    public void setEnviarParaValidacao(Boolean enviarParaValidacao){
        this.enviarParaValidacao = enviarParaValidacao;
    }

    public Boolean getCriarEventoRisco(){
        return criarEventoRisco;
    }

    public void setCriarEventoRisco(Boolean criarEventoRisco){
        this.criarEventoRisco = criarEventoRisco;
    }

    public Boolean getConsultarUnidade(){
        return consultarUnidade;
    }

    public void setConsultarUnidade(Boolean consultarUnidade){
        this.consultarUnidade = consultarUnidade;
    }

    public OrgaoDTO getOrgao(){
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao){
        this.orgao = orgao;
    }
}
