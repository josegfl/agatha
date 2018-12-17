package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.CalculoRiscoBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

@Entity
@Audited
@Table(name = "tb_calculo_risco")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_calculo_risco SET ic_excluido=true WHERE id_calculo_risco=?")
@NoArgsConstructor
public class CalculoRisco extends CalculoRiscoBase {

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "calculoRisco", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PesoEventoRisco> pesos;

    @JsonIgnore
    @OneToOne(mappedBy = "calculoRiscoResidual", fetch = FetchType.LAZY)
    private EventoRisco eventoRisco;

    public List<PesoEventoRisco> getPesos() {
        return pesos;
    }

    public void setPesos(List<PesoEventoRisco> pesos) {
        this.pesos = pesos;
    }

    public EventoRisco getEventoRisco() {
        return eventoRisco;
    }

    public void setEventoRisco(EventoRisco eventoRisco) {
        this.eventoRisco = eventoRisco;
    }
}
