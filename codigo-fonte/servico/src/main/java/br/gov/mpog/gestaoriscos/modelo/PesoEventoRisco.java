package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.PesoEventoRiscoBase;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_peso_evento_risco")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_peso_evento_risco SET ic_excluido=true WHERE id_peso_evento_risco=?")
@NoArgsConstructor
public class PesoEventoRisco extends PesoEventoRiscoBase {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_calculo_risco", nullable = false)
    private CalculoRisco calculoRisco;

    public CalculoRisco getCalculoRisco() {
        return calculoRisco;
    }

    public void setCalculoRisco(CalculoRisco calculoRisco) {
        this.calculoRisco = calculoRisco;
    }
}
