package br.gov.mpog.gestaoriscos.modelo.base;

import br.gov.mpog.gestaoriscos.modelo.EntidadeBase;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Audited
@MappedSuperclass
public class PesoEventoRiscoBase extends EntidadeBase {

    @Id
    @Column(name = "id_peso_evento_risco")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_peso_evento_risco", name = "sk_peso_evento_risco")
    @GeneratedValue(generator = "sk_peso_evento_risco", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nu_peso")
    private Integer peso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
}
