package br.gov.mpog.gestaoriscos.repositorio;


import br.gov.mpog.gestaoriscos.modelo.Orgao;

import java.util.List;

public interface OrgaoCustomRepositorio {

    List<Orgao> listarOrgaos(Long idOrgaoRaiz);

    List<Orgao> filtrar(String nomeOrgao);

    List<String> searchByNome(String nome);

    List<String> searchByNomeAndOrgaoPaiId(String nome, Long orgaoPaiId);

    List<String> searchByNomeAndOrgaoMP(String nome);
}
