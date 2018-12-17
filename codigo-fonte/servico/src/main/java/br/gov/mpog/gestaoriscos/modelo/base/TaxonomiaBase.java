package br.gov.mpog.gestaoriscos.modelo.base;

import br.gov.mpog.gestaoriscos.modelo.EntidadeBase;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Audited
@MappedSuperclass
public class TaxonomiaBase extends EntidadeBase {

    @Id
    @Column(name = "id_taxonomia")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_taxonomia", name = "sk_taxonomia")
    @GeneratedValue(generator = "sk_taxonomia", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = AnnotationNumberUtil.L200, name = "ds_taxonomia", nullable = false)
    private String descricao;

    @NotNull
    @Column(length = AnnotationNumberUtil.L200, name = "ds_search", nullable = false)
    private String search;

    @NotNull
    @Column(name = "dt_cadastro", nullable = false)
    private Calendar dtCadastro;

    @Column(name = "ds_justificativa", columnDefinition = "text")
    private String justificativa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}