package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.GlossarioBase;
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
@Table(name = "tb_glossario")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_glossario SET ic_excluido=true WHERE id_glossario=?")
@AttributeOverrides({
        @AttributeOverride(name = "descricao", column = @Column(name = "ds_glossario"))

})
public class Glossario extends GlossarioBase {

    @Id
    @Column(name = "id_glossario")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_glossario", name = "sk_glossario")
    @GeneratedValue(generator = "sk_glossario", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}