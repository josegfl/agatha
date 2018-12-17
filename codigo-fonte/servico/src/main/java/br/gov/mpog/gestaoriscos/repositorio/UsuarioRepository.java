package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Permissao entity.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT u FROM Permissao p INNER JOIN p.usuario u INNER JOIN p.perfil pf WHERE pf.id = :perfilId")
    List<Usuario> findByPerfil(@Param("perfilId") Long perfilId);

    Usuario findByCpf(String cpf);

    @Query("SELECT u FROM Usuario u " +
            " LEFT JOIN FETCH u.orgao orgao " +
            " LEFT JOIN FETCH u.permissoes permissao " +
            " WHERE u.id = ?1 ")
    Usuario findById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.cpf = ?1 AND NOT u.id = ?2 ")
    Usuario findByCpfExcludeId(String cpf, Long id);
}
