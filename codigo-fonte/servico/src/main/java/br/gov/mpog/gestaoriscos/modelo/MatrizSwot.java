package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.MatrizSwotBase;
import br.gov.mpog.gestaoriscos.util.HashCodeUtil;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_matriz_swot")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_matriz_swot SET ic_excluido=true WHERE id_matriz_swot=?")
public class MatrizSwot extends MatrizSwotBase {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_matriz", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoMatriz tipoMatriz;

    @ManyToOne
    @JoinColumn(name = "id_analise", nullable = false)
    private Analise analise;

    public TipoMatriz getTipoMatriz() {
        return tipoMatriz;
    }

    public void setTipoMatriz(TipoMatriz tipoMatriz) {
        this.tipoMatriz = tipoMatriz;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }
}