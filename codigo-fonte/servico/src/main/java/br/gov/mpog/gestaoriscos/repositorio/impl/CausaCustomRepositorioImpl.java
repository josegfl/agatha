package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.repositorio.CausaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.util.QueryBuilderUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CausaCustomRepositorioImpl implements CausaCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Causa> findBySearchAndOrgaoId(String descricao, Long orgaoId) {
        String queryString = QueryBuilderUtil.taxonomiaOrgaoBaseBuildQueryFindBySearchAndOrgaoId("Causa", orgaoId, descricao);

        if (queryString == null) {
            return new ArrayList<>();
        } else {
            return em.createQuery(queryString, Causa.class).getResultList();
        }
    }
}