package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_evento_consequencia")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_evento_consequencia SET ic_excluido=true WHERE id_evento_consequencia=?")
public class EventoConsequencia extends EntidadeBaseEventoRisco {

    @Id
    @Column(name = "id_evento_consequencia")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_evento_consequencia", name = "sk_evento_consequencia")
    @GeneratedValue(generator = "sk_evento_consequencia", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consequencia", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Consequencia consequencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consequencia getConsequencia() {
        return consequencia;
    }

    public void setConsequencia(Consequencia consequencia) {
        this.consequencia = consequencia;
    }
}