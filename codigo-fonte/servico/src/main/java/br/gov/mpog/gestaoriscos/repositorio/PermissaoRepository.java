package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.Permissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Permissao entity.
 */
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Page<Permissao> findByUsuarioIdOrderByUsuarioIdAsc(Long id, Pageable pageable);

    @Query(value = "SELECT pfl FROM Permissao prm INNER JOIN prm.perfil pfl INNER JOIN prm.usuario usr WHERE usr.cpf = :cpf AND LOWER(pfl.nome) = LOWER(:perfilNome) AND prm.excluido = false")
    Perfil findPerfilByUsuarioIdAndNomeIgnoreCase(@Param("cpf") String cpf, @Param("perfilNome") String perfilNome);

    List<Permissao> findByUsuario_Cpf(String cpf);

    Permissao findByUsuarioIdAndPerfilId(Long usuarioId, Long perfilId);

    @Modifying
    @Query("UPDATE Permissao set excluido = true WHERE id = ?1")
    void deletePermissaoById(Long idPermissao);

    @Modifying
    @Query("UPDATE Permissao set excluido = true WHERE id IN :idList")
    void deletePermissaoById(@Param("idList") List<Long> idList);
}
