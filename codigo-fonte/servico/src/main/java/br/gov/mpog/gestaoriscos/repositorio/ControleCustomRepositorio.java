package br.gov.mpog.gestaoriscos.repositorio;


import br.gov.mpog.gestaoriscos.modelo.Controle;

import java.util.List;

public interface ControleCustomRepositorio{

    List<Controle> findBySearchAndOrgaoId(String descricao, Long orgaoId);
}
