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
import java.math.BigDecimal;

@Audited
@MappedSuperclass
public class CalculoRiscoBase extends EntidadeBase {

    @Id
    @Column(name = "id_calculo_risco")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_calculo_risco", name = "sk_calculo_risco")
    @GeneratedValue(generator = "sk_calculo_risco", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nu_media_peso", scale = 2)
    private BigDecimal mediaPeso;

    @Column(name = "nu_frequencia")
    private Integer frequencia;

    @Column(name = "nu_nivel", scale = 2)
    private BigDecimal nivel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMediaPeso() {
        return mediaPeso;
    }

    public void setMediaPeso(BigDecimal mediaPeso) {
        this.mediaPeso = mediaPeso;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public BigDecimal getNivel() {
        return nivel;
    }

    public void setNivel(BigDecimal nivel) {
        this.nivel = nivel;
    }
}
