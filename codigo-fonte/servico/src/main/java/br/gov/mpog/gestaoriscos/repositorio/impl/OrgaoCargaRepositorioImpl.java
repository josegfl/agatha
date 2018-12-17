package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.NaturezaJuridica;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCargaRepositorio;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrgaoCargaRepositorioImpl implements OrgaoCargaRepositorio {

    private static final String MSG_ERRO_AO_INSERIR = "Erro ao inserir o Lote de unidades. Erro na Unidade: ";
    private static final String MSG_ERRO_AO_ATUALIZAR = "Erro ao atualizar o Lote de unidades. Erro na Unidade: ";

    private static final String SQL_LISTAR_TODOS_IDS_ORGAOS_EXISTENTES = "" +
            "select CAST(id_orgao as BIGINT) id_orgao  from gestaoriscos.tb_orgao order by id_orgao";

    private static final String SQL_INSERIR_CARGA = "INSERT INTO gestaoriscos.tb_orgao " +
            "(id_orgao, no_orgao, id_orgao_superior, sg_orgao, id_categoria_unidade, id_natureza_juridica) " +
            "VALUES(:id, :nome, CAST(:paiId as INTEGER), :sigla, CAST(:categoriaId as INTEGER), CAST(:naturezaJuricaId as INTEGER))";

    private static final String SQL_ATUALIZAR_CARGA = "UPDATE gestaoriscos.tb_orgao " +
            "set no_orgao = :nome, id_orgao_superior = CAST(:paiId AS INTEGER), sg_orgao = :sigla, " +
            "id_categoria_unidade = CAST(:categoriaId as INTEGER), id_natureza_juridica = CAST(:naturezaJuricaId as INTEGER) " +
            "where id_orgao = :id";

    @PersistenceContext
    private EntityManager em;

    private void definirParametros(final SQLQuery query, final Orgao orgao) {
        Long paiId = orgaoSuperior(orgao);
        Short naturezaJuricaId = naturezaJurica(orgao);
        query.setLong("id", orgao.getId());
        query.setString("nome", orgao.getNome());
        query.setString("sigla", orgao.getSigla());
        if (paiId == null) {
            query.setString("paiId", null);
        } else {
            query.setLong("paiId", paiId.longValue());
        }

        Optional.ofNullable((orgao.getCategoriaUnidade().getId())).ifPresent(c -> query.setInteger("categoriaId", c.intValue()));

        if (naturezaJuricaId == null) {
            query.setString("naturezaJuricaId", null);
        } else {
            query.setShort("naturezaJuricaId", naturezaJuricaId.shortValue());
        }
    }

    private Long orgaoSuperior(final Orgao orgao) {
        Orgao pai = orgao.getOrgaoPai();
        if (pai.getId() == null) {
            return null;
        }
        return pai.getId();
    }

    private Short naturezaJurica(final Orgao orgao) {
        NaturezaJuridica naturezaJuridica = orgao.getNaturezaJuridica();
        if (naturezaJuridica.getId() == null) {
            return null;
        }
        return naturezaJuridica.getId();
    }

    @Transactional
    public void inserir(List<Orgao> orgaos) {
        for (Orgao orgao : orgaos) {
            executarSQL(SQL_INSERIR_CARGA, orgao, MSG_ERRO_AO_INSERIR);
        }
    }

    @Transactional
    public void atualizar(List<Orgao> orgaos) {
        for (Orgao orgao : orgaos) {
            executarSQL(SQL_ATUALIZAR_CARGA, orgao, MSG_ERRO_AO_ATUALIZAR);
        }
    }

    private void executarSQL(final String sql, final Orgao orgao, final String msgErro) {
        SQLQuery qry = getSqlQuery(sql);
        definirParametros(qry, orgao);
        qry.executeUpdate();
    }

    private SQLQuery getSqlQuery(String sql) {
        Session session = em.unwrap(Session.class);
        return session.createSQLQuery(sql);
    }

    @SuppressWarnings("unchecked")
    public LinkedList<Long> listarExistentes() {
        LinkedList<Long> existentes = new LinkedList<>();
        Query query = em.createNativeQuery(SQL_LISTAR_TODOS_IDS_ORGAOS_EXISTENTES);
        List<BigInteger> ids2 = query.getResultList();
        for (BigInteger i : ids2) {
            existentes.add(i.longValue());
        }
        return existentes;
    }

}
