package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.base.TaxonomiaBase;

public class TaxonomiaDTO extends TaxonomiaBase {

    private TipoTaxonomiaDTO tipo;

    private StatusTaxonomiaDTO status;

    private OrgaoDTO orgao;

    private EventoDTO evento;

    private CausaDTO causa;

    private ConsequenciaDTO consequencia;

    private ControleDTO controle;

    private AgrupamentoTaxonomiaDTO agrupamento;

    public TipoTaxonomiaDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoTaxonomiaDTO tipo) {
        this.tipo = tipo;
    }

    public StatusTaxonomiaDTO getStatus() {
        return status;
    }

    public void setStatus(StatusTaxonomiaDTO status) {
        this.status = status;
    }

    public OrgaoDTO getOrgao() {
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao) {
        this.orgao = orgao;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public CausaDTO getCausa() {
        return causa;
    }

    public void setCausa(CausaDTO causa) {
        this.causa = causa;
    }

    public ConsequenciaDTO getConsequencia() {
        return consequencia;
    }

    public void setConsequencia(ConsequenciaDTO consequencia) {
        this.consequencia = consequencia;
    }

    public ControleDTO getControle() {
        return controle;
    }

    public void setControle(ControleDTO controle) {
        this.controle = controle;
    }

    public AgrupamentoTaxonomiaDTO getAgrupamento() {
        return agrupamento;
    }

    public void setAgrupamento(AgrupamentoTaxonomiaDTO agrupamento) {
        this.agrupamento = agrupamento;
    }
}