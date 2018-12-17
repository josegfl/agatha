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
@Table(name = "tb_objetivo_controle")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_objetivo_controle SET ic_excluido=true WHERE id_objetivo_controle=?")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_objetivo_controle"))

})
public class ObjetivoControle extends EntidadeBaseNome {

    @Id
    @Column(name = "id_objetivo_controle")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_objetivo_controle", name = "sk_objetivo_controle")
    @GeneratedValue(generator = "sk_objetivo_controle", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}