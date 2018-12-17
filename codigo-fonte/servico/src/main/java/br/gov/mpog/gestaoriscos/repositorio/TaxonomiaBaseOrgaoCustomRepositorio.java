package br.gov.mpog.gestaoriscos.repositorio;

import java.util.List;

public interface TaxonomiaBaseOrgaoCustomRepositorio {

    List<String> searchByDescricao(String descricao, String entidade);
}
