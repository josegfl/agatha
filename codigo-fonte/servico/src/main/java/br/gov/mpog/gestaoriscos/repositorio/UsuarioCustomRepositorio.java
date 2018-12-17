package br.gov.mpog.gestaoriscos.repositorio;


import java.util.List;

public interface UsuarioCustomRepositorio{

    List<String> searchByDescricao(String descricao);

}
