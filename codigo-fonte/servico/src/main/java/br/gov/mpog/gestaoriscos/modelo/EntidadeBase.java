package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Audited
@MappedSuperclass
public class EntidadeBase {

    @NotNull
    @NotAudited
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }
}