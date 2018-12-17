package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.AnaliseBase;
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
import java.util.List;

@Entity
@Audited
@Table(name = "tb_analise")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_analise SET ic_excluido=true WHERE id_analise=?")
public class Analise extends AnaliseBase {

    @ManyToOne
    @JoinColumn(name = "id_orgao")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Orgao orgao;

    @ManyToOne
    @JoinColumn(name = "id_secretaria")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Orgao secretaria;

    @JsonIgnore
    @Where(clause = "ic_excluido='false'")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "analise", orphanRemoval = true)
    private List<MatrizSwot> matrizes;

    @JsonIgnore
    @OneToOne(mappedBy = "analise")
    private Processo processo;

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public Orgao getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Orgao secretaria) {
        this.secretaria = secretaria;
    }

    public List<MatrizSwot> getMatrizes() {
        return matrizes;
    }

    public void setMatrizes(List<MatrizSwot> matrizes) {
        this.matrizes = matrizes;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}