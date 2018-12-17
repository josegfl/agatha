package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Audited
@MappedSuperclass
public class TaxonomiaOrgaoBase extends EntidadeBaseDescricaoStatusSearch {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orgao")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Orgao orgao;

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }
}
