package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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

@Entity
@Audited
@Table(name = "tb_responsavel")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_responsavel SET ic_excluido=true WHERE id_responsavel=?")
public class Responsavel{

    @Id
    @Column(name = "id_responsavel")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_responsavel", name = "sk_responsavel")
    @GeneratedValue(generator = "sk_responsavel", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotAudited
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    @ManyToOne
    @JoinColumn(name = "id_processo", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Processo processo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Usuario usuario;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public boolean isExcluido(){
        return excluido;
    }

    public void setExcluido(boolean excluido){
        this.excluido = excluido;
    }

    public Processo getProcesso(){
        return processo;
    }

    public void setProcesso(Processo processo){
        this.processo = processo;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}