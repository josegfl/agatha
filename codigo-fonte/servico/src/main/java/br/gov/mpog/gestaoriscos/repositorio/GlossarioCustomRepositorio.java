package br.gov.mpog.gestaoriscos.repositorio;


import java.util.List;

public interface GlossarioCustomRepositorio{

    List<String> searchByTermo(String descricao);

    List<String> searchByDescricao(String descricao);
}
