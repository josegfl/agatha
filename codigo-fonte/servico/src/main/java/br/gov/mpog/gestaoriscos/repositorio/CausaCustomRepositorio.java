package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Causa;

import java.util.List;

public interface CausaCustomRepositorio {

    List<Causa> findBySearchAndOrgaoId(String descricao, Long orgaoId);
}
