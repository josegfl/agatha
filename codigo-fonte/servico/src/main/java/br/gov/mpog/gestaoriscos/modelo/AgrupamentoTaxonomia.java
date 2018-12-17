package br.gov.mpog.gestaoriscos.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Audited
@Table(name = "tb_agrupamento_taxonomia")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_agrupamento_taxonomia SET ic_excluido=true WHERE id_agrupamento_taxonomia=?")
public class AgrupamentoTaxonomia{

    @Id
    @Column(name = "id_agrupamento_taxonomia")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_agrupamento_taxonomia", name = "sk_agrupamento_taxonomia")
    @GeneratedValue(generator = "sk_agrupamento_taxonomia", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_taxonomia_padrao", nullable = false)
    private Taxonomia taxonomia;

    @JsonIgnore
    @Where(clause = "ic_excluido = 'false'")
    @OneToMany(mappedBy = "agrupamento", fetch = FetchType.LAZY)
    private List<Taxonomia> taxonomias;

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

    public Taxonomia getTaxonomia(){
        return taxonomia;
    }

    public void setTaxonomia(Taxonomia taxonomia){
        this.taxonomia = taxonomia;
    }

    public List<Taxonomia> getTaxonomias(){
        return taxonomias;
    }

    public void setTaxonomias(List<Taxonomia> taxonomias){
        this.taxonomias = taxonomias;
    }
}