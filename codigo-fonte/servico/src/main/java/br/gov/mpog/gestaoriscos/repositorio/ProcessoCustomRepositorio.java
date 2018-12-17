package br.gov.mpog.gestaoriscos.repositorio;


import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoListagemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProcessoCustomRepositorio{

    List<String> searchByDescricao(String descricao);

    Page<ProcessoListagemDTO> findByFilters(Pageable pageable, String cpf, String orgao, String search, Long statusId, Long dtInicio, Long dtFim, Boolean isGestor, Boolean isAnalista);
}
