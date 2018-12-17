package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Orgao entity.
 */
public interface OrgaoRepository extends JpaRepository<Orgao, Long>{

    @Query(value = "SELECT o FROM Usuario u INNER JOIN u.orgao o WHERE u.cpf = :cpf")
    Orgao findByUsuarioCPF(@Param("cpf") String cpf);

    @Query(value = "SELECT pai FROM Orgao o LEFT OUTER JOIN o.orgaoPai pai WHERE o.id = :orgaoId")
    Orgao findOrgaoPaiById(@Param("orgaoId") Long orgaoId);

    @Query(value = "SELECT new br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO(o.id, o.nome, o.sigla) FROM Orgao o " +
            " LEFT JOIN o.orgaoPai pai " +
            " WHERE o.id = :orgaoId OR (pai.id = :orgaoId " +
            " AND o.categoriaUnidade.id IN (:listaIdCategoria)) " +
            " ORDER BY o.orgaoPai.id, o.sigla ASC ")
    List<OrgaoDTO> findUnidadePeloIdLimitadoPorIdCategoria(@Param("orgaoId") Long orgaoId, @Param("listaIdCategoria") List<Long> listaIdCategoria);
}
