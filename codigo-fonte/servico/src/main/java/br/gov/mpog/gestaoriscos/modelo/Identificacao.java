package br.gov.mpog.gestaoriscos.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Audited
@Table(name = "tb_identificacao")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_identificacao SET ic_excluido=true WHERE id_identificacao=?")
public class Identificacao extends EntidadeBase {

    @Id
    @Column(name = "id_identificacao")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_identificacao", name = "sk_identificacao")
    @GeneratedValue(generator = "sk_identificacao", strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "identificacao")
    private Processo processo;

    @JsonIgnore
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(mappedBy = "identificacao", fetch = FetchType.LAZY)
    private List<EventoRisco> eventos;

    public Identificacao() {
    }

    public Identificacao(Processo processo) {
        setProcesso(processo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public List<EventoRisco> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoRisco> eventos) {
        this.eventos = eventos;
    }
}
