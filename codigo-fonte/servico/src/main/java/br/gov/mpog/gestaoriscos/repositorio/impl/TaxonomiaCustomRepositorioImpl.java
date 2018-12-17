package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.DateUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class TaxonomiaCustomRepositorioImpl implements TaxonomiaCustomRepositorio {

    private static String PARENTESE = "') ";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JpaContext jpaContext;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchByDescricao(String descricao, Boolean hasAgrupamento) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT t.descricao FROM Taxonomia t INNER JOIN t.status stt LEFT OUTER JOIN t.agrupamento agr WHERE LOWER(stt.nome) LIKE LOWER('não avaliado') AND ");
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (int i = 0; i < search.length; i++) {

            if (search[i].length() >= AnnotationNumberUtil.L3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(t.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(search[i]) + AnnotationStringUtil.QUERY_LIKE + PARENTESE);
                hasQuery = true;
            }
        }

        if (!hasQuery) {
            return new ArrayList<>();
        }

        return em.createQuery(queryStringBuffer.toString(), String.class).getResultList();
    }

    @Override
    public List<Taxonomia> findBySearch(String descricao, Long tipoId) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT t FROM Taxonomia t INNER JOIN t.tipo tp INNER JOIN t.status stt WHERE LOWER(stt.nome) LIKE LOWER('não avaliado') AND tp.id = " + tipoId + AnnotationStringUtil.QUERY_AND);
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (int i = 0; i < search.length; i++) {

            if (search[i].length() >= AnnotationNumberUtil.L3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(t.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(search[i]) + AnnotationStringUtil.QUERY_LIKE + PARENTESE);
                hasQuery = true;
            }
        }

        if (!hasQuery) {
            return new ArrayList<>();
        }

        return em.createQuery(queryStringBuffer.toString(), Taxonomia.class).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Taxonomia> listarTaxonomias(Pageable pageable, String descricao, String orgao, Long inicio, Long fim, Long tipoId, Long statusId) {
        String queryCountString = "SELECT COUNT(DISTINCT t.id)";
        String querySelectString = "SELECT DISTINCT t";
        String queryString = " FROM Taxonomia t "
                + " INNER JOIN t.tipo tp "
                + " INNER JOIN t.status stt "
                + " INNER JOIN t.orgao o ";

        String condition = " WHERE ";

        queryString += condition;
        queryString += " stt.id = '" + statusId + "'";
        condition = AnnotationStringUtil.QUERY_AND;

        if (descricao != null) {
            queryString += condition;
            queryString += "LOWER(t.descricao) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(descricao) + AnnotationStringUtil.QUERY_LIKE + PARENTESE;
            condition = AnnotationStringUtil.QUERY_AND;
        }

        if (tipoId != null) {
            queryString += condition;
            queryString += " tp.id = '" + tipoId + "'";
            condition = AnnotationStringUtil.QUERY_AND;
        }

        if (inicio != null && fim != null) {
            String dtInical = DateUtil.getDBTimestamp(DateUtil.getDateMinimizedHours(new Date(inicio)));
            String dtFim = DateUtil.getDBTimestamp(DateUtil.getDateMaximizedHours(new Date(fim)));

            queryString += condition;
            queryString += " t.dtCadastro BETWEEN '" + dtInical + "' AND '" + dtFim + "'";
            condition = AnnotationStringUtil.QUERY_AND;
        }

        if (orgao != null) {
            queryString += condition;
            queryString += "LOWER(o.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + orgao + AnnotationStringUtil.QUERY_LIKE + PARENTESE;
        }

        String queryOrderString = "ORDER BY t.dtCadastro ASC";

        Query query = em.createQuery(querySelectString + queryString + queryOrderString, Taxonomia.class);
        Query queryCount = em.createQuery(queryCountString + queryString, Long.class);

        int firstResult = (pageable.getPageNumber()) * pageable.getPageSize();

        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());

        List<Taxonomia> permissaoList = query.getResultList();

        Optional.ofNullable(permissaoList)
                .filter(permissaos -> !permissaos.isEmpty());

        Page<Taxonomia> results = new PageImpl<Taxonomia>(new ArrayList<Taxonomia>(), pageable, (Long) queryCount.getResultList().get(0));
        results = new PageImpl<Taxonomia>(permissaoList, pageable, results.getTotalElements());

        return results;
    }
}