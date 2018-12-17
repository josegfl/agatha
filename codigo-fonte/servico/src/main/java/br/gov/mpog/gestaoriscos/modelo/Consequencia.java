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
@Table(name = "tb_consequencia")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_consequencia SET ic_excluido=true WHERE id_consequencia=?")
@AttributeOverrides({
        @AttributeOverride(name = "descricao", column = @Column(name = "ds_consequencia"))

})
public class Consequencia extends TaxonomiaOrgaoBase {

    @Id
    @Column(name = "id_consequencia")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_consequencia", name = "sk_consequencia")
    @GeneratedValue(generator = "sk_consequencia", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
