package br.gov.mpog.gestaoriscos.repositorio;


import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaxonomiaCustomRepositorio{

    List<String> searchByDescricao(String descricao, Boolean hasAgrupamento);

        List<Taxonomia> findBySearch(String descricao, Long tipoId);

    Page<Taxonomia> listarTaxonomias(Pageable pageable, String descricao, String orgao, Long inicio, Long fim, Long tipoTaxonomiaId, Long statusId);
}
