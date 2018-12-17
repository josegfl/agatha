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
@Table(name = "tb_decisao_processo")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_decisao_processo SET ic_excluido=true WHERE id_decisao_processo=?")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_decisao"))

})
public class DecisaoProcesso extends EntidadeBaseNome {

    @Id
    @Column(name = "id_decisao_processo")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_decisao_processo", name = "sk_decisao_processo")
    @GeneratedValue(generator = "sk_decisao_processo", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}