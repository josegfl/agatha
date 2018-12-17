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
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_impacto_calculadora")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_impacto_calculadora SET ic_excluido=true WHERE id_impacto_calculadora=?")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_impacto_calculadora"))

})
@NoArgsConstructor
public class ImpactoCalculadora extends EntidadeBaseItemCalculadora {

    @Id
    @Column(name = "id_impacto_calculadora")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_impacto_calculadora", name = "sk_impacto_calculadora")
    @GeneratedValue(generator = "sk_impacto_calculadora", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nu_peso")
    private Long peso;

    @NotNull
    @Column(name = "ic_desabilitado", columnDefinition = "boolean default true", nullable = false)
    private Boolean desabilitado;

    public ImpactoCalculadora(ImpactoCalculadora impactoBase, Calculadora calculadora) {
        setExcluido(false);
        setCalculadora(calculadora);
        setNome(impactoBase.getNome());
        setPeso(impactoBase.getPeso());
        setDesabilitado(impactoBase.getDesabilitado());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Boolean getDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(Boolean desabilitado) {
        this.desabilitado = desabilitado;
    }
}
