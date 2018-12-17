package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaBaseCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaxonomiaBaseCustomRepositorioImpl implements TaxonomiaBaseCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchByDescricao(String descricao, String entidade) {
        StringBuilder queryStringBuffer = new StringBuilder();

        queryStringBuffer.append("SELECT DISTINCT e.descricao FROM ").append(entidade).append(" e WHERE ");
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (int i = 0; i < search.length; i++) {

            if (search[i].length() >= 3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer
                        .append(" LOWER(e.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE)
                        .append(StringUtil.removerAcento(search[i]))
                        .append(AnnotationStringUtil.QUERY_LIKE).append("') ");
                hasQuery = true;
            }
        }

        if (!hasQuery) {
            return new ArrayList<>();
        }

        return em.createQuery(queryStringBuffer.toString(), String.class).getResultList();
    }
}