package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Evento;

import java.util.List;

public interface EventoCustomRepositorio {

    List<Evento> findBySearchAndOrgaoId(String descricao, Long orgaoId);
}
