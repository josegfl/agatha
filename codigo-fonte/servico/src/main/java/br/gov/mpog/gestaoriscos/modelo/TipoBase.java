package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by luizfernando on 23/06/17.
 */
@MappedSuperclass
public class TipoBase {

    @Column(length = AnnotationNumberUtil.L200, name = "no_base")
    private String nome;

    @NotNull
    @Column(name = "ic_excluido", columnDefinition = "boolean default false")
    private boolean excluido;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }
}
