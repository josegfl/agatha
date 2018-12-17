package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.util.QueryBuilderUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConsequenciaCustomRepositorioImpl implements ConsequenciaCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Consequencia> findBySearchAndOrgaoId(String descricao, Long orgaoId) {
        String queryString = QueryBuilderUtil.taxonomiaOrgaoBaseBuildQueryFindBySearchAndOrgaoId("Consequencia", orgaoId, descricao);

        if (queryString == null) {
            return new ArrayList<>();
        } else {
            return em.createQuery(queryString, Consequencia.class).getResultList();
        }
    }
}