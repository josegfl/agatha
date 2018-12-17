package br.gov.mpog.gestaoriscos.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Audited
@Table(name = "tb_resposta_risco")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_resposta_risco SET ic_excluido=true WHERE id_resposta_risco=?")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_resposta_risco"))
})
public class RespostaRisco extends EntidadeBaseNome {

    @Id
    @Column(name = "id_resposta_risco")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_resposta_risco", name = "sk_resposta_risco")
    @GeneratedValue(generator = "sk_resposta_risco", strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "respostaRisco", fetch = FetchType.LAZY)
    private List<EventoRisco> eventos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EventoRisco> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoRisco> eventos) {
        this.eventos = eventos;
    }
}