package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Audited
@Table(name = "tb_plano_controle")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_plano_controle SET ic_excluido=true WHERE id_plano_controle=?")
public class PlanoControle extends EntidadeBaseEventoRisco {

    @Id
    @Column(name = "id_plano_controle")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_plano_controle", name = "sk_plano_controle")
    @GeneratedValue(generator = "sk_plano_controle", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_controle", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Controle controle;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_controle", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoControle tipoControle;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_objetivo_controle", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ObjetivoControle objetivo;

    @Column(length = AnnotationNumberUtil.L200, name = "no_area_responsavel")
    private String areaResponsavel;

    @Column(length = AnnotationNumberUtil.L200, name = "no_responsavel")
    private String responsavel;

    @Column(length = AnnotationNumberUtil.L200, name = "no_interveniente")
    private String interveniente;

    @Column(length = AnnotationNumberUtil.L4000, name = "ds_implementacao")
    private String implementacao;

    @Column(name = "dt_inicio")
    private Calendar dtInicio;

    @Column(name = "dt_conclusao")
    private Calendar dtConclusao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

    public TipoControle getTipoControle() {
        return tipoControle;
    }

    public void setTipoControle(TipoControle tipoControle) {
        this.tipoControle = tipoControle;
    }

    public ObjetivoControle getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ObjetivoControle objetivo) {
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