package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "tb_natureza")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_natureza SET ic_excluido=true WHERE id_natureza=?")
@AttributeOverrides({
        @AttributeOverride(name = "descricao", column = @Column(name = "ds_natureza"))
})
public class Natureza extends EntidadeBaseDescricaoStatusSearch {

    @Id
    @Column(name = "id_natureza")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_natureza", name = "sk_natureza")
    @GeneratedValue(generator = "sk_natureza", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
