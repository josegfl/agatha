package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.repositorio.UsuarioCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UsuarioCustomRepositorioImpl implements UsuarioCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchByDescricao(String nome) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT u.nome FROM Usuario u WHERE ");
        nome = nome.trim();
        String[] search = nome.split(" ");

        for (int i = 0; i < search.length; i++) {

            if (search[i].length() >= AnnotationNumberUtil.L3) {
                if (i != 0) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(u.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + search[i] + AnnotationStringUtil.QUERY_LIKE + "') ");
            }
        }

        return em.createQuery(queryStringBuffer.toString(), String.class).getResultList();
    }
}