package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Consequencia;

import java.util.List;

public interface ConsequenciaCustomRepositorio {

    List<Consequencia> findBySearchAndOrgaoId(String descricao, Long orgaoId);
}
