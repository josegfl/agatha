package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.repositorio.ControleCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.util.QueryBuilderUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ControleCustomRepositorioImpl implements ControleCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Controle> findBySearchAndOrgaoId(String descricao, Long orgaoId) {
        String queryString = QueryBuilderUtil.taxonomiaOrgaoBaseBuildQueryFindBySearchAndOrgaoId("Controle", orgaoId, descricao);

        if (queryString == null) {
            return new ArrayList<>();
        } else {
            return em.createQuery(queryString, Controle.class).getResultList();
        }
    }
}