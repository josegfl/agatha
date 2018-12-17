package br.gov.mpog.gestaoriscos.repositorio;


import br.gov.mpog.gestaoriscos.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissaoCustomRepositorio {

    Page<Usuario> listarPermissaos(Pageable pageable, String usuario, Long perfilId, Long orgao);

}
