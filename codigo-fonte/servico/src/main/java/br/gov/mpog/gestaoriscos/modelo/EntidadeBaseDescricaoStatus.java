package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Audited
@MappedSuperclass
public class EntidadeBaseDescricaoStatus extends EntidadeBase {

    @Column(length = AnnotationNumberUtil.L1000, name = "ds_base")
    private String descricao;

    @NotNull
    @Column(name = "ic_ativo", columnDefinition = "boolean default true")
    private boolean status;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
