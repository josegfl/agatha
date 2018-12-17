package br.gov.mpog.gestaoriscos.modelo;

import lombok.NoArgsConstructor;
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
@Table(name = "tb_probabilidade_calculadora")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_probabilidade_calculadora SET ic_excluido=true WHERE id_probabilidade_calculadora=?")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_probabilidade_calculadora"))

})
@NoArgsConstructor
public class ProbabilidadeCalculadora extends EntidadeBaseItemCalculadora {

    @Id
    @Column(name = "id_probabilidade_calculadora")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_probabilidade_calculadora", name = "sk_probabilidade_calculadora")
    @GeneratedValue(generator = "sk_probabilidade_calculadora", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nu_frequencia")
    private Long frequencia;

    public ProbabilidadeCalculadora(ProbabilidadeCalculadora probabilidadeBase, Calculadora calculadora) {
        setExcluido(false);
        setCalculadora(calculadora);
        setNome(probabilidadeBase.getNome());
        setFrequencia(probabilidadeBase.getFrequencia());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Long frequencia) {
        this.frequencia = frequencia;
    }
}
