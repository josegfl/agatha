package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoListagemDTO;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.DateUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProcessoCustomRepositorioImpl implements ProcessoCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchByDescricao(String descricao) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT concat(p.macroprocesso, '/', p.processo) FROM Processo p WHERE ");
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (int i = 0; i < search.length; i++) {

            if (search[i].length() >= 3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(p.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(search[i]) + AnnotationStringUtil.QUERY_LIKE + "') ");
                hasQuery = true;
            }
        }

        if (!hasQuery) {
            return new ArrayList<>();
        }

        return em.createQuery(queryStringBuffer.toString(), String.class).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<ProcessoListagemDTO> findByFilters(Pageable pageable, String cpf, String orgao, String search, Long statusId, Long inicio, Long fim, Boolean isGestor, Boolean isAnalista) {
        String queryCountString = "SELECT COUNT(DISTINCT pro.id) ";
        String querySelectString = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.ProcessoListagemDTO(pro, '" + cpf + "') ";
        String queryString = " FROM Processo pro "
                + " INNER JOIN pro.analise anl "
                + " LEFT OUTER JOIN pro.macroprocesso mac "
                + " LEFT OUTER JOIN anl.orgao org "
                + " LEFT OUTER JOIN anl.secretaria sec "
                + " INNER JOIN pro.status stt "
                + " INNER JOIN pro.gestor gst "
                + " INNER JOIN pro.responsaveis rsp "
                + " INNER JOIN rsp.usuario usr ";

        String condition = " WHERE ";
        if (orgao != null) {
            queryString += condition;
            queryString += "( LOWER(org.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + orgao + AnnotationStringUtil.QUERY_LIKE + "') ";
            queryString += "OR LOWER(sec.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + orgao + AnnotationStringUtil.QUERY_LIKE + "') ) ";
            condition = AnnotationStringUtil.QUERY_AND;
        }
        if (search != null) {
            queryString += condition;
            queryString += "LOWER(pro.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + StringUtil.removerAcento(search) + AnnotationStringUtil.QUERY_LIKE + "') ";
            condition = AnnotationStringUtil.QUERY_AND;
        }
        if (statusId != null) {
            queryString += condition;
            queryString += " stt.id = " + statusId;
            condition = AnnotationStringUtil.QUERY_AND;
        }
        if (inicio != null && fim != null) {
            String dtInical = DateUtil.getDBTimestamp(DateUtil.getDateMinimizedHours(new Date(inicio)));
            String dtFim = DateUtil.getDBTimestamp(DateUtil.getDateMaximizedHours(new Date(fim)));

            queryString += condition;
            queryString += " pro.dtCadastro BETWEEN '" + dtInical + "' AND '" + dtFim + "'";
            condition = AnnotationStringUtil.QUERY_AND;
        }
        if (isAnalista) {
            queryString += condition;
            queryString += " ( usr.cpf = '" + cpf + "'";
            queryString += !isGestor ? " ) " : "";
            condition = " OR ";
        }
        if (isGestor) {
            queryString += condition;
            queryString += !isAnalista ? " ( " : "";
            queryString += " gst.cpf = '" + cpf + "' ) ";
        }

        String queryOrderString = " GROUP BY pro.id ORDER BY pro.dtCadastro DESC ";

        Query query = em.createQuery(querySelectString + queryString + queryOrderString, ProcessoListagemDTO.class);
        Query queryCount = em.createQuery(queryCountString + queryString, Long.class);

        int firstResult = (pageable.getPageNumber()) * pageable.getPageSize();

        query.setFirstResult(firstResult);
        query.setMaxResults(pageable.getPageSize());

        List<ProcessoListagemDTO> permissaoList = query.getResultList();

        Optional.ofNullable(permissaoList)
                .filter(permissaos -> !permissaos.isEmpty());

        Page<ProcessoListagemDTO> results = new PageImpl<>(new ArrayList<>(), pageable, (Long) queryCount.getResultList().get(0));
        results = new PageImpl<>(permissaoList, pageable, results.getTotalElements());

        return results;
    }

}