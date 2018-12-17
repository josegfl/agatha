package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.EntidadeBaseOrgao;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Audited
@Entity
@Table(name = "tb_orgao")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id_orgao")),
        @AttributeOverride(name = "nome", column = @Column(name = "no_orgao")),
        @AttributeOverride(name = "sigla", column = @Column(name = "sg_orgao"))
})
@NoArgsConstructor
public class Orgao extends EntidadeBaseOrgao {

    /**
     * "codigoTipoUnidade" Considerado como "categoriaUnidade"
     */
    @ManyToOne
    @JoinColumn(name = "id_categoria_unidade")
    private CategoriaUnidade categoriaUnidade;

    @ManyToOne
    @JoinColumn(name = "id_natureza_juridica")
    private NaturezaJuridica naturezaJuridica;

    @ManyToOne
    @JoinColumn(name = "id_orgao_superior")
    private Orgao orgaoPai;

    @OneToMany(mappedBy = "orgaoPai")
    private List<Orgao> orgaosFilhos;

    @OneToMany(mappedBy = "orgao")
    private List<Usuario> usuarios;

    @Transient
    private Long idCategoriaUnidade;

    @Transient
    private Short idNaturezaJuridica;

    public Orgao(Long id) {
        this();
        setId(id);
    }

    public Orgao(Long id, String nome, String sigla, CategoriaUnidade categoriaUnidade, NaturezaJuridica naturezaJuridica, Orgao pai) {
        setId(id);
        setNome(nome);
        setSigla(sigla);
        this.categoriaUnidade = categoriaUnidade;
        this.naturezaJuridica = naturezaJuridica;
        this.orgaoPai = pai;
    }

    public Orgao(final Long id, final String nome, final String sigla, final Long orgaoPaiId, final Long idCategoriaUnidade, final Short idNaturezaJuridica) {
        this(id);
        setNome(nome);
        setSigla(sigla);
        this.orgaoPai = new Orgao(orgaoPaiId);

        this.idCategoriaUnidade = idCategoriaUnidade;
        this.categoriaUnidade = new CategoriaUnidade(idCategoriaUnidade);

        this.idNaturezaJuridica = idNaturezaJuridica;
        this.naturezaJuridica = new NaturezaJuridica(idNaturezaJuridica);
    }

    public CategoriaUnidade getCategoriaUnidade() {
        return categoriaUnidade;
    }

    public void setCategoriaUnidade(CategoriaUnidade categoriaUnidade) {
        this.categoriaUnidade = categoriaUnidade;
    }

    public NaturezaJuridica getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(NaturezaJuridica naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public Orgao getOrgaoPai() {
        return orgaoPai;
    }

    public void setOrgaoPai(Orgao orgaoPai) {
        this.orgaoPai = orgaoPai;
    }

    public List<Orgao> getOrgaosFilhos() {
        return orgaosFilhos;
    }

    public void setOrgaosFilhos(List<Orgao> orgaosFilhos) {
        this.orgaosFilhos = orgaosFilhos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Long getIdCategoriaUnidade() {
        return idCategoriaUnidade;
    }

    public void setIdCategoriaUnidade(Long idCategoriaUnidade) {
        this.idCategoriaUnidade = idCategoriaUnidade;
    }

    public Short getIdNaturezaJuridica() {
        return idNaturezaJuridica;
    }

    public void setIdNaturezaJuridica(Short idNaturezaJuridica) {
        this.idNaturezaJuridica = idNaturezaJuridica;
    }
}
