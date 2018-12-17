package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.HashCodeUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_perfil")
public class Perfil {

    @Id
    @Column(name = "id_perfil")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_perfil", name = "sk_perfil")
    @GeneratedValue(generator = "sk_perfil", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = AnnotationNumberUtil.L100, name = "no_perfil")
    private String nome;

    @Column(name = "prioridade", unique = true)
    private Integer prioridade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }
}
