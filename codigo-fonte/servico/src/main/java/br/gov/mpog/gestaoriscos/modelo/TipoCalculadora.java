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
@Table(name = "tb_tipo_calculadora")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_tipo_calculadora SET ic_excluido=true WHERE id_tipo_calculadora=?")
@AttributeOverrides({
    @AttributeOverride(name = "nome", column = @Column(name = "no_tipo_calculadora"))
})
public class TipoCalculadora extends EntidadeBaseNome {

    @Id
    @Column(name = "id_tipo_calculadora")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_tipo_calculadora", name = "sk_tipo_calculadora")
    @GeneratedValue(generator = "sk_tipo_calculadora", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
