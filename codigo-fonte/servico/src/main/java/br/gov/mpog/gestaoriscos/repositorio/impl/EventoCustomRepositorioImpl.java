package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.repositorio.EventoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.util.QueryBuilderUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventoCustomRepositorioImpl implements EventoCustomRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Evento> findBySearchAndOrgaoId(String descricao, Long orgaoId) {
        String queryString = QueryBuilderUtil.taxonomiaOrgaoBaseBuildQueryFindBySearchAndOrgaoId("Evento", orgaoId, descricao);

        if (queryString == null) {
            return new ArrayList<>();
        } else {
            return em.createQuery(queryString, Evento.class).getResultList();
        }
    }
}