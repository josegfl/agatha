package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Orgao;

import java.util.LinkedList;
import java.util.List;

public interface OrgaoCargaRepositorio {

    void inserir(final List<Orgao> orgaos);

    void atualizar(final List<Orgao> orgaos);

    LinkedList<Long> listarExistentes();

}
