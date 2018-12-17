package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaJuridicaDTO;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Audited
@NoArgsConstructor
@Entity
@Table(name = "tb_natureza_juridica")
public class NaturezaJuridica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_natureza_juridica")
    private Short id;

    @NotNull
    @Column(length = AnnotationNumberUtil.L30, name = "no_natureza_juridica")
    private String nome;

    public NaturezaJuridica(Short id) {
        this.id = id;
    }

    public NaturezaJuridica(NaturezaJuridicaDTO naturezaJuridica) {
        this.id = naturezaJuridica.getId();
        this.nome = naturezaJuridica.getNome();
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}