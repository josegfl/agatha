package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.CategoriaUnidade;
import br.gov.mpog.gestaoriscos.modelo.base.EntidadeBaseOrgao;

public class CategoriaUnidadeDTO extends EntidadeBaseOrgao {

    public CategoriaUnidadeDTO() {
        super();
    }

    public CategoriaUnidadeDTO(Long id) {
        super(id);
    }

    public CategoriaUnidadeDTO(CategoriaUnidade categoriaUnidade) {
        super(categoriaUnidade);
    }
}
