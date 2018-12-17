package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.Permissao;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
public class PermissaoDTO {

    private Long id;

    private UsuarioDTO usuario;

    private String usuarioNome;

    private PerfilDTO perfil;

    private boolean excluido;

    private Date dtCadastro;

    public PermissaoDTO(Permissao permissao) {
        Optional.ofNullable(permissao).ifPresent(permissaoAux -> {
            this.id = permissaoAux.getId();
            this.excluido = permissaoAux.isExcluido();
            this.dtCadastro = permissaoAux.getDtCadastro();

            Optional.ofNullable(permissaoAux.getPerfil()).ifPresent(perfilAux -> {
                this.setPerfil(new PerfilDTO(perfilAux.getId(), perfilAux.getNome()));
            });

            Optional.ofNullable(permissaoAux.getUsuario()).ifPresent(usuarioAux -> {
                this.setUsuario(new UsuarioDTO(usuarioAux));
            });
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public PerfilDTO getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDTO perfil) {
        this.perfil = perfil;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}
