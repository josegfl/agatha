package br.gov.mpog.gestaoriscos.modelo.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsuarioPerfilPK implements Serializable {

    private static final long serialVersionUID = 856689056789499109L;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_perfil")
    private Long idPerfil;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }
}
