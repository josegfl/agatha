package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.Usuario;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String cpf;

    private OrgaoDTO orgao;

    private String email;

    private List<PermissaoDTO> permissoes;

    public UsuarioDTO(Usuario usuario) {
        Optional.ofNullable(usuario).ifPresent(usuarioAux -> {
            this.id = usuarioAux.getId();
            this.nome = usuarioAux.getNome();
            this.cpf = usuarioAux.getCpf();

            //-> Adiciona apenas dados simples do PAI para não entrar em LOOP
            Optional.ofNullable(usuarioAux.getOrgao()).ifPresent(orgaoAux -> {
                this.orgao = new OrgaoDTO(orgaoAux.getId(), orgaoAux.getNome(), orgaoAux.getSigla(),
                        orgaoAux.getIdCategoriaUnidade(), orgaoAux.getIdNaturezaJuridica());
            });
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public OrgaoDTO getOrgao() {
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao) {
        this.orgao = orgao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PermissaoDTO> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissaoDTO> permissoes) {
        this.permissoes = permissoes;
    }
}
