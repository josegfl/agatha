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

@Audited
@MappedSuperclass
public class MatrizSwotBase extends EntidadeBase {

    @Id
    @Column(name = "id_matriz_swot")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_matriz_swot", name = "sk_matriz_swot")
    @GeneratedValue(generator = "sk_matriz_swot", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = AnnotationNumberUtil.L200, name = "ds_matriz_swot")
    private String descricao;

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
}