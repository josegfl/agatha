package br.gov.mpog.gestaoriscos.repositorio.impl;

import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class OrgaoCustomRepositorioImpl implements OrgaoCustomRepositorio {

    public static final String JPQL_LISTAR_ORGAOS_RAIZES = "select new br.gov.mpog.gestaoriscos.modelo.Orgao(" +
            "o.id, o.nome, o.sigla, c, n, pai" +
            ") from Orgao o INNER JOIN o.categoriaUnidade c LEFT JOIN o.naturezaJuridica n LEFT JOIN o.orgaoPai pai " +
            "where o.orgaoPai.id is null";

    public static final String JPQL_LISTAR_ORGAOS_POR_ID = "select new br.gov.mpog.gestaoriscos.modelo.Orgao(" +
            "o.id, o.nome, o.sigla, c, n, pai" +
            ") from Orgao o INNER JOIN o.categoriaUnidade c LEFT JOIN o.naturezaJuridica n LEFT JOIN o.orgaoPai pai " +
            "where o.id = :idOrgao";

    // Ordenação fixa utilizando como regra a ordem de categoria unidade a seguir: 
    // Ente (#ID 5) / Órgão (#ID 4) / Entidade (#ID 3) / Unidade Administrativa (#ID 1) / Unidade Colegiada (#ID 2)
    public static final String JPQL_LISTAR_ORGAOS_FILHOS = "select new br.gov.mpog.gestaoriscos.modelo.Orgao(" +
            "o.id, o.nome, o.sigla, c, n, pai" +
            ") from Orgao o INNER JOIN o.categoriaUnidade c LEFT JOIN o.naturezaJuridica n LEFT JOIN o.orgaoPai pai " +
            "where o.orgaoPai = :orgaoPai " +
            "order by case c.id " +
            "when 5 then 1 " +
            "when 4 then 2 " +
            "when 3 then 3 " +
            "when 1 then 4 " +
            "else 5 " +
            "end, UPPER(o.nome) asc";

    public static final String JPQL_LISTARUSUARIOS_POR_ORGAO = "select new br.gov.mpog.gestaoriscos.modelo.Usuario(u.id,  u.nome,  u.cpf, u.orgao.id) " +
            "from Usuario u " +
            "where u.orgao = :orgaoPai";

    public static final String JPQL_FILTRARORGAOS_POR_NOME = "SELECT new br.gov.mpog.gestaoriscos.modelo.Orgao(o.id, o.nome, o.sigla, c, n, pai) FROM Orgao o INNER JOIN o.categoriaUnidade c LEFT JOIN o.naturezaJuridica n LEFT JOIN o.orgaoPai pai WHERE LOWER(o.nome) LIKE CONCAT('%', LOWER(:nomeOrgao), '%') ORDER BY o.nome ASC";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Orgao> listarOrgaos(Long idOrgaoRaiz) {
        if (idOrgaoRaiz != null) {
            Orgao orgao = orgaoPorID(idOrgaoRaiz);
            buscarFilhos(orgao);
            return Collections.singletonList(orgao);
        }
        List<Orgao> raizes = orgaosRaizes();
        for (Orgao raiz : raizes) {
            buscarFilhos(raiz);
        }
        return raizes;
    }

    public List<Orgao> filtrar(String nomeOrgao) {
        return em.createQuery(JPQL_FILTRARORGAOS_POR_NOME, Orgao.class)
                .setParameter("nomeOrgao", nomeOrgao)
                .getResultList();
    }

    @Override
    public List<String> searchByNome(String nome) {
        String queryString = "SELECT DISTINCT org.nome FROM Orgao org WHERE LOWER(org.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + nome + AnnotationStringUtil.QUERY_LIKE + "') ORDER BY nome ASC";

        return em.createQuery(queryString, String.class).getResultList();
    }

    @Override
    public List<String> searchByNomeAndOrgaoPaiId(String nome, Long orgaoPaiId) {
        String queryString = "SELECT DISTINCT org.nome FROM Orgao org INNER JOIN org.orgaoPai pai WHERE LOWER(org.nome) LIKE LOWER('" + AnnotationStringUtil.QUERY_LIKE + nome + AnnotationStringUtil.QUERY_LIKE + "') AND pai.id = :orgaoPaiId ORDER BY org.nome ASC";

        return em.createQuery(queryString, String.class)
                .setParameter("orgaoPaiId", orgaoPaiId)
                .getResultList();
    }

    @Override
    public List<String> searchByNomeAndOrgaoMP(String nome) {
        String queryString = " WITH RECURSIVE subordinates AS ( " +
                " SELECT id_orgao, id_orgao_superior, no_orgao  FROM " +
                " tb_orgao WHERE id_orgao = 2981 " +
                " UNION SELECT e.id_orgao, e.id_orgao_superior, e.no_orgao  FROM tb_orgao e INNER JOIN subordinates s ON s.id_orgao = e.id_orgao_superior " +
                ") SELECT no_orgao FROM subordinates sub " +
                "WHERE LOWER(sub.no_orgao) LIKE LOWER(:nomeOrgao) ORDER BY sub.no_orgao ASC";

        return em.createNativeQuery(queryString)
                .setParameter("nomeOrgao", AnnotationStringUtil.QUERY_LIKE + nome + AnnotationStringUtil.QUERY_LIKE)
                .getResultList();
    }

    private List<Orgao> orgaosRaizes() {
        return em.createQuery(JPQL_LISTAR_ORGAOS_RAIZES, Orgao.class)
                .getResultList();
    }

    private Orgao orgaoPorID(Long idOrgaoRaiz) {
        return em.createQuery(JPQL_LISTAR_ORGAOS_POR_ID, Orgao.class)
                .setParameter("idOrgao", idOrgaoRaiz)
                .getSingleResult();
    }

    private void buscarFilhos(Orgao orgao) {
        orgao.setOrgaosFilhos(
                em.createQuery(JPQL_LISTAR_ORGAOS_FILHOS, Orgao.class)
                        .setParameter("orgaoPai", orgao)
                        .getResultList()
        );

        orgao.setUsuarios(em.createQuery(JPQL_LISTARUSUARIOS_POR_ORGAO, Usuario.class)
                .setParameter("orgaoPai", orgao)
                .getResultList());

    }
}