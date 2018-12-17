package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Audited
@MappedSuperclass
public class EntidadeBaseEventoTaxonomia extends EntidadeBase {

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento_risco", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private EventoRisco eventoRisco;

    public EventoRisco getEventoRisco() {
        return eventoRisco;
    }

    public void setEventoRisco(EventoRisco eventoRisco) {
        this.eventoRisco = eventoRisco;
    }
}