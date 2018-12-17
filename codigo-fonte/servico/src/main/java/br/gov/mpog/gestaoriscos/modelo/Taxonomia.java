package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.TaxonomiaBase;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_taxonomia")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_taxonomia SET ic_excluido=true WHERE id_taxonomia=?")
public class Taxonomia extends TaxonomiaBase {

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_taxonomia", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoTaxonomia tipo;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_taxonomia", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StatusTaxonomia status;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orgao", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Orgao orgao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_causa")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Causa causa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consequencia")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Consequencia consequencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_controle")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Controle controle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agrupamento_taxonomia")
    private AgrupamentoTaxonomia agrupamento;

    public TipoTaxonomia getTipo() {
        return tipo;
    }

    public void setTipo(TipoTaxonomia tipo) {
        this.tipo = tipo;
    }

    public StatusTaxonomia getStatus() {
        return status;
    }

    public void setStatus(StatusTaxonomia status) {
        this.status = status;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Causa getCausa() {
        return causa;
    }

    public void setCausa(Causa causa) {
        this.causa = causa;
    }

    public Consequencia getConsequencia() {
        return consequencia;
    }

    public void setConsequencia(Consequencia consequencia) {
        this.consequencia = consequencia;
    }

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

    public AgrupamentoTaxonomia getAgrupamento() {
        return agrupamento;
    }

    public void setAgrupamento(AgrupamentoTaxonomia agrupamento) {
        this.agrupamento = agrupamento;
    }
}