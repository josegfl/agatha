package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.AgrupamentoTaxonomia;
import br.gov.mpog.gestaoriscos.repositorio.AgrupamentoTaxonomiaCustomRepositorio;
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
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AgrupamentoTaxonomiaCustomRepositorioImpl implements AgrupamentoTaxonomiaCustomRepositorio{

    @Autowired
    private JpaContext jpaContext;

    @Override
    @SuppressWarnings("unchecked")
    public Page<AgrupamentoTaxonomia> listarAgrupamentoTaxonomias(Pageable pageable, String descricao, Long inicio, Long fim, Long tipoId){
        EntityManager em = jpaContext.getEntityManagerByManagedType(AgrupamentoTaxonomia.class);
        String queryCountString = "SELECT COUNT(DISTINCT agTx.id)";
        String querySelectString = "SELECT DISTINCT agTx ";
        String queryString = " FROM AgrupamentoTaxonomia agTx "
                + " INNER JOIN agTx.taxonomia t "
                + " INNER JOIN agTx.taxonomias txs "
                + " INNER JOIN t.tipo tp ";

        String condition = " WHERE ";
        if(descricao != null){
            queryString += condition;
            queryString += "LOWER(t.descricao) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(descricao) + AnnotationStringUtil.QUERY_LIKE + "') ";
            condition = " AND ";
        }

        if(tipoId != null){
            queryString += condition;
            queryString += " tp.id = '" + tipoId + "'";
            condition = " AND ";
        }

        if(inicio != null && fim != null){
            String dtInical = DateUtil.getDBTimestamp(DateUtil.getDateMinimizedHours(new Date(inicio)));
            String dtFim = DateUtil.getDBTimestamp(DateUtil.getDateMaximizedHours(new Date(fim)));

            queryString += condition;
            queryString += " txs.dtCadastro BETWEEN '" + dtInical + "' AND '" + dtFim + "'";
        }

        String queryOrderString = " ORDER BY agTx.id ASC ";

        Query query = em.createQuery(querySelectString + queryString + queryOrderString, AgrupamentoTaxonomia.class);
        Query queryCount = em.createQuery(queryCountString + queryString, Long.class);

        int firstResult = (pageable.getPageNumber()) * pageable.getPageSize();

        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());

        List<AgrupamentoTaxonomia> permissaoList = query.getResultList();

        Optional.ofNullable(permissaoList)
                .filter(permissaos -> !permissaos.isEmpty());

        Page<AgrupamentoTaxonomia> results = new PageImpl<AgrupamentoTaxonomia>(new ArrayList<AgrupamentoTaxonomia>(), pageable, (Long) queryCount.getResultList().get(0));
        results = new PageImpl<AgrupamentoTaxonomia>(permissaoList, pageable, results.getTotalElements());

        return results;
    }
}