package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Audited
@MappedSuperclass
public class EntidadeBaseItemCalculadora extends EntidadeBaseNome {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_calculadora")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Calculadora calculadora;

    public Calculadora getCalculadora() {
        return calculadora;
    }

    public void setCalculadora(Calculadora calculadora) {
        this.calculadora = calculadora;
    }
}