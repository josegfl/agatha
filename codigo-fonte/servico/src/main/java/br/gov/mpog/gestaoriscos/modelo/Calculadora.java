package br.gov.mpog.gestaoriscos.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "tb_calculadora")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_calculadora SET ic_excluido=true WHERE id_calculadora=?")
@NoArgsConstructor
public class Calculadora{

    @Id
    @Column(name = "id_calculadora")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_calculadora", name = "sk_calculadora")
    @GeneratedValue(generator = "sk_calculadora", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotAudited
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_calculadora")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoCalculadora tipo;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "calculadora", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ImpactoCalculadora> impactos;

    @JsonIgnore
    @OrderBy("id")
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "calculadora", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProbabilidadeCalculadora> probabilidades;

    @JoinColumn(name = "id_processo")
    @OneToOne(fetch = FetchType.LAZY)
    private Processo processo;

    public Calculadora(Calculadora calculadoraBase, Processo processo){
        this.excluido = false;
        this.processo = processo;
        this.tipo = calculadoraBase.getTipo();
        this.impactos = new ArrayList<>(0);
        this.probabilidades = new ArrayList<>(0);

        for(ImpactoCalculadora impactoCalculadora : calculadoraBase.getImpactos()){
            this.impactos.add(new ImpactoCalculadora(impactoCalculadora, this));
        }

        for(ProbabilidadeCalculadora probabilidadeCalculadora : calculadoraBase.getProbabilidades()){
            this.probabilidades.add(new ProbabilidadeCalculadora(probabilidadeCalculadora, this));
        }
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public boolean isExcluido(){
        return excluido;
    }

    public void setExcluido(boolean excluido){
        this.excluido = excluido;
    }

    public TipoCalculadora getTipo(){
        return tipo;
    }

    public void setTipo(TipoCalculadora tipo){
        this.tipo = tipo;
    }

    public List<ImpactoCalculadora> getImpactos(){
        return impactos;
    }

    public void setImpactos(List<ImpactoCalculadora> impactos){
        this.impactos = impactos;
    }

    public List<ProbabilidadeCalculadora> getProbabilidades(){
        return probabilidades;
    }

    public void setProbabilidades(List<ProbabilidadeCalculadora> probabilidades){
        this.probabilidades = probabilidades;
    }

    public Processo getProcesso(){
        return processo;
    }

    public void setProcesso(Processo processo){
        this.processo = processo;
    }
}
