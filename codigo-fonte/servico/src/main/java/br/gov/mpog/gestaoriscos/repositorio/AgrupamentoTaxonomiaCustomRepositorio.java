package br.gov.mpog.gestaoriscos.repositorio;


import br.gov.mpog.gestaoriscos.modelo.AgrupamentoTaxonomia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgrupamentoTaxonomiaCustomRepositorio{

    Page<AgrupamentoTaxonomia> listarAgrupamentoTaxonomias(Pageable pageable, String descricao, Long inicio, Long fim, Long tipoTaxonomiaId);
}