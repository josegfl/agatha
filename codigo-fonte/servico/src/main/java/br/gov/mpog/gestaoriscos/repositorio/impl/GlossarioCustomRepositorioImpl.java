package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.repositorio.GlossarioCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GlossarioCustomRepositorioImpl implements GlossarioCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchByTermo(String descricao) {
        List<String> result = searchBy(descricao, "termo");
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchByDescricao(String descricao) {
        List<String> result = searchBy(descricao, "descricao");
        return result;
    }

    private List<String> searchBy(String descricao, String query){
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT g." + query + " FROM Glossario g WHERE ");
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (int i = 0; i < search.length; i++) {

            if (search[i].length() >= AnnotationNumberUtil.L3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(g." + query + "Search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(search[i]) + AnnotationStringUtil.QUERY_LIKE + "') ");
                hasQuery = true;
            }
        }

        if (!hasQuery) {
            return new ArrayList<>();
        }

        return em.createQuery(queryStringBuffer.toString(), String.class).getResultList();
    }
}