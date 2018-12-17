package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaBaseOrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.util.QueryBuilderUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaxonomiaBaseOrgaoCustomRepositorioImpl implements TaxonomiaBaseOrgaoCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<String> searchByDescricao(String descricao, String entidade) {
        String queryString = QueryBuilderUtil.taxonomiaOrgaoBaseBuildQuerySearchByDescricao(entidade, descricao);

        if (queryString == null) {
            return new ArrayList<>();
        } else {
            return em.createQuery(queryString, String.class).getResultList();
        }
    }
}