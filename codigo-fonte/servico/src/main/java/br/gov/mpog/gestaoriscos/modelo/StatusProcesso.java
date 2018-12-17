package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.HashCodeUtil;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_status_processo")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_status_processo SET ic_excluido=true WHERE id_status_processo=?")
public class StatusProcesso extends EntidadeBaseNome {

    @Id
    @Column(name = "id_status_processo")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_status_processo", name = "sk_status_processo")
    @GeneratedValue(generator = "sk_status_processo", strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}