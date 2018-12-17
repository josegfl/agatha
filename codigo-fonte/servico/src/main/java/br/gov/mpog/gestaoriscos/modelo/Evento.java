package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Audited
@Table(name = "tb_evento")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_evento SET ic_excluido=true WHERE id_evento=?")
@AttributeOverrides({
        @AttributeOverride(name = "descricao", column = @Column(name = "ds_evento"))

})
public class Evento extends TaxonomiaOrgaoBase {

    @Id
    @Column(name = "id_evento")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_evento", name = "sk_evento")
    @GeneratedValue(generator = "sk_evento", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
