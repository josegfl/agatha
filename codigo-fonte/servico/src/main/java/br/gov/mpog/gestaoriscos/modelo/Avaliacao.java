package br.gov.mpog.gestaoriscos.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_avaliacao")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_avaliacao SET ic_excluido=true WHERE id_avaliacao=?")
@NoArgsConstructor
public class Avaliacao extends EntidadeBase {

    @Id
    @Column(name = "id_avaliacao")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_avaliacao", name = "sk_avaliacao")
    @GeneratedValue(generator = "sk_avaliacao", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ic_ignorar_risco_inerente", columnDefinition = "boolean default false")
    private Boolean ignorarRiscoInerente;

    @JsonIgnore
    @OneToOne(mappedBy = "avaliacao")
    private Processo processo;

    public Avaliacao(Processo processo) {
        setProcesso(processo);
        setIgnorarRiscoInerente(false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIgnorarRiscoInerente() {
        return ignorarRiscoInerente;
    }

    public void setIgnorarRiscoInerente(Boolean ignorarRiscoInerente) {
        this.ignorarRiscoInerente = ignorarRiscoInerente;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}