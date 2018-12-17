package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PermissaoCustomRepositorioImpl implements PermissaoCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Usuario> listarPermissaos(Pageable pageable, String usuario, Long perfilId, Long orgao) {
        String queryCountString = "SELECT COUNT(DISTINCT u.id)";
        String querySelectString = "SELECT DISTINCT u";
        String queryString = " FROM Permissao p "
                + " INNER JOIN p.usuario u "
                + " INNER JOIN p.perfil pe "
                + " INNER JOIN u.orgao o ";

        String condition = " WHERE ";
        if (usuario != null) {
            queryString += condition;
            queryString += "LOWER(u.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + usuario + AnnotationStringUtil.QUERY_LIKE + "') ";
            condition = " AND ";
        }

        if (perfilId != null) {
            queryString += condition;
            queryString += " pe.id = '" + perfilId + "'";
            condition = " AND ";
        }

        if (orgao != null) {
            queryString += condition;
            queryString += " o.id = '" + orgao + "'";
        }

        String queryOrderString = "ORDER BY u.nome asc";

        Query query = em.createQuery(querySelectString + queryString + queryOrderString, Usuario.class);
        Query queryCount = em.createQuery(queryCountString + queryString, Long.class);

        int firstResult = (pageable.getPageNumber()) * pageable.getPageSize();

        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());

        List<Usuario> permissaoList = query.getResultList();

        Optional.ofNullable(permissaoList)
                .filter(permissaos -> !permissaos.isEmpty());

        Page<Usuario> results = new PageImpl<Usuario>(new ArrayList<Usuario>(), pageable, (Long) queryCount.getResultList().get(0));
        results = new PageImpl<Usuario>(permissaoList, pageable, results.getTotalElements());

        return results;
    }

}