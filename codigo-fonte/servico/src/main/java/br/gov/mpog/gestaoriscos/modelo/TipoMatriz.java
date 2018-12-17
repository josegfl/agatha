package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.HashCodeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tb_tipo_matriz")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_tipo_matriz SET ic_excluido=true WHERE id_tipo_matriz=?")
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "no_tipo_matriz"))
})
public class TipoMatriz extends EntidadeBaseNome {

    @Id
    @Column(name = "id_tipo_matriz")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_tipo_matriz", name = "sk_tipo_matriz")
    @GeneratedValue(generator = "sk_tipo_matriz", strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoMatriz")
    private List<MatrizSwot> matrizes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MatrizSwot> getMatrizes() {
        return matrizes;
    }

    public void setMatrizes(List<MatrizSwot> matrizes) {
        this.matrizes = matrizes;
    }
}