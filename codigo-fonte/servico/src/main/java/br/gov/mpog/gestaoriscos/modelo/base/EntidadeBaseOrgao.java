package br.gov.mpog.gestaoriscos.modelo.base;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Audited
@MappedSuperclass
public class EntidadeBaseOrgao {

    @Id
    @Column(name = "id_categoria_unidade")
    private Long id;

    @NotNull
    @Column(name = "no_categoria")
    private String nome;

    @NotNull
    @Column(name = "sg_categoria_unidade")
    private String sigla;

    public EntidadeBaseOrgao() {
    }

    public EntidadeBaseOrgao(Long id) {
        this.id = id;
    }

    public EntidadeBaseOrgao(EntidadeBaseOrgao categoriaUnidade) {
        this(categoriaUnidade.getId());
        setNome(categoriaUnidade.getNome());
        setSigla(categoriaUnidade.getSigla());
    }

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
