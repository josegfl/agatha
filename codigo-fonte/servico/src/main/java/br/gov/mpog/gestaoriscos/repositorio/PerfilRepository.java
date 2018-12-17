package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Permissao entity.
 */
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

    Perfil findByNomeIgnoreCase(String nome);

    @Query("SELECT perfil FROM Usuario usuario " +
            " INNER JOIN usuario.permissoes permissao " +
            " INNER JOIN permissao.perfil perfil " +
            " WHERE permissao.excluido = false" +
            " AND usuario.cpf = :cpf ")
    List<Perfil> findByCPF(@Param("cpf") String cpf);
}
