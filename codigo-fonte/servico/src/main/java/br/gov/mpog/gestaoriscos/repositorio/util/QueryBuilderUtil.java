package br.gov.mpog.gestaoriscos.repositorio.util;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.StringUtil;

/**
 * Utility class for handling pagination.
 * <p>
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">Github API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class QueryBuilderUtil {

    private QueryBuilderUtil() {
        throw new IllegalAccessError("Classe Utiliataria");
    }

    public static String taxonomiaOrgaoBaseBuildQuerySearchByDescricao(String entidade, String descricao) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT entity.descricao FROM ").append(entidade).append(" entity LEFT OUTER JOIN entity.orgao org WHERE org.id IS NULL AND ( ");

        if (!taxonomiaOrgaoBaseBuildSearchParameters(queryStringBuffer, descricao)) {
            return null;
        }

        queryStringBuffer.append(" ORDER BY entity.descricao ASC");

        return queryStringBuffer.toString();
    }

    public static String taxonomiaOrgaoBaseBuildQueryFindBySearchAndOrgaoId(String entidade, Long orgaoId, String descricao) {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("SELECT DISTINCT entity FROM ").append(entidade).append(" entity LEFT OUTER JOIN entity.orgao org WHERE entity.status = true AND ");

        if (orgaoId != null) {
            queryStringBuffer.append(" (org.id IS NULL  OR org.id = ").append(orgaoId).append(") AND ");
        } else {
            queryStringBuffer.append(" org.id IS NULL AND ");
        }

        if (!taxonomiaOrgaoBaseBuildSearchParameters(queryStringBuffer, descricao)) {
            return null;
        }

        queryStringBuffer.append(" ORDER BY entity.descricao ASC");

        return queryStringBuffer.toString();
    }

    private static Boolean taxonomiaOrgaoBaseBuildSearchParameters(StringBuffer queryStringBuffer, String descricao) {
        descricao = descricao.trim();
        String[] search = descricao.split(" ");

        Boolean hasQuery = false;
        for (String aSearch : search) {

            if (aSearch.length() >= AnnotationNumberUtil.L3) {
                if (hasQuery) {
                    queryStringBuffer.append(" OR ");
                }
                queryStringBuffer.append(" LOWER(entity.search) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE).append(StringUtil.removerAcento(aSearch)).append(AnnotationStringUtil.QUERY_LIKE).append("') ");
                hasQuery = true;
            }
        }

        queryStringBuffer.append(" ) ");

        return hasQuery;
    }
}
