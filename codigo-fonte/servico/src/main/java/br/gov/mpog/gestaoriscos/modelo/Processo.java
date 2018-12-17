package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.ProcessoBase;
import br.gov.mpog.gestaoriscos.util.HashCodeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Audited
@Table(name = "tb_processo")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_processo SET ic_excluido=true WHERE id_processo=?")
public class Processo extends ProcessoBase {

    @ManyToOne
    @JoinColumn(name = "id_status_processo", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StatusProcesso status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_macroprocesso")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Macroprocesso macroprocesso;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Usuario gestor;

    @JsonIgnore
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "processo", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Responsavel> responsaveis;

    @JsonIgnore
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "processo", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProcessoAnexo> anexos;

    @JoinColumn(name = "id_analise")
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Analise analise;

    @JoinColumn(name = "id_identificacao")
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Identificacao identificacao;

    @JoinColumn(name = "id_avaliacao")
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Avaliacao avaliacao;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "processo", fetch = FetchType.LAZY, orphanRemoval = true)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private List<Calculadora> calculadoras;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_decisao")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private DecisaoProcesso decisao;

    public StatusProcesso getStatus() {
        return status;
    }

    public void setStatus(StatusProcesso status) {
        this.status = status;
    }

    public Macroprocesso getMacroprocesso() {
        return macroprocesso;
    }

    public void setMacroprocesso(Macroprocesso macroprocesso) {
        this.macroprocesso = macroprocesso;
    }

    public Usuario getGestor() {
        return gestor;
    }

    public void setGestor(Usuario gestor) {
        this.gestor = gestor;
    }

    public List<Responsavel> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<Responsavel> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public List<ProcessoAnexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ProcessoAnexo> anexos) {
        this.anexos = anexos;
    }

    public Analise getAnalise() { return analise; }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public Identificacao getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Identificacao identificacao) {
        this.identificacao = identificacao;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Calculadora> getCalculadoras() {
        return calculadoras;
    }

    public void setCalculadoras(List<Calculadora> calculadoras) {
        this.calculadoras = calculadoras;
    }

    public DecisaoProcesso getDecisao() {
        return decisao;
    }

    public void setDecisao(DecisaoProcesso decisao) {
        this.decisao = decisao;
    }
}