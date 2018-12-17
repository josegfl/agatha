package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.EntidadeBaseOrgao;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaUnidadeDTO;
import org.hibernate.envers.Audited;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Audited
@Entity
@Table(name = "tb_categoria_unidade")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_categoria"))

})
public class CategoriaUnidade extends EntidadeBaseOrgao {

    public CategoriaUnidade() {
        super();
    }

    public CategoriaUnidade(Long id) {
        super(id);
    }

    public CategoriaUnidade(CategoriaUnidadeDTO categoriaUnidade) {
        super(categoriaUnidade);
    }
}
