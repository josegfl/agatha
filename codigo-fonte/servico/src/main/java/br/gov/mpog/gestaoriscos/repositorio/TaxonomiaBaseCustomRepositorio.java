package br.gov.mpog.gestaoriscos.repositorio;

import java.util.List;

public interface TaxonomiaBaseCustomRepositorio {

    List<String> searchByDescricao(String descricao, String entidade);

}
