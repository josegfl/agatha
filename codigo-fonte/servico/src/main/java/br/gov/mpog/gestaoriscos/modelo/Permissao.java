package br.gov.mpog.gestaoriscos.modelo;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Audited
@Table(name = "tb_permissao")
@NoArgsConstructor
public class Permissao {

    @Id
    @Column(name = "id_permissao")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_permissao", name = "sk_permissao")
    @GeneratedValue(generator = "sk_permissao", strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @NotAudited
    @NotNull
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    @NotNull
    @Column(name = "dt_cadastro", nullable = false)
    private Date dtCadastro;

    public Permissao(Permissao permissao) {
        this.id = permissao.getId();
        this.excluido = permissao.isExcluido();

        this.perfil = permissao.perfil;
        this.usuario = permissao.getUsuario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
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
