package br.gov.mpog.gestaoriscos.util.auditoria;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ENTIDADE CRIADA PARA MAPEAR A TABELA DE REVISAO USANDO SEQUENCE PARA H2
 */
@Entity
@Table(name = "tb_revisao", schema = "aud_gestaoriscos")
@RevisionEntity(value = TipoHistoricoAcervoListener.class)
public class HistoricoAcervoAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "hibernate_envers_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_envers_sequence")
    @RevisionNumber
    @Column(name = "rev")
    private Integer id;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private Long timestamp;

    @Column(name = "no_responsavel")
    private String noUsuario;

    @Column(name = "nu_cpf_usuario")
    private String nuCpfUsuario;

    @Column(name = "nu_ip_usuario")
    private String nuIpUsuario;

    /**
     * Obter o valor do atributo <code>id</code> da classe.
     *
     * @return O atributo <code>id</code>.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Atribuir o valor do <code>pId</code> no atribuito <code>id</code> da Classe.
     *
     * @param pId Parâmetro de Entrada
     */
    public void setId(Integer pId) {
        id = pId;
    }

    /**
     * Obter o valor do atributo <code>timestamp</code> da classe.
     *
     * @return O atributo <code>timestamp</code>.
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Atribuir o valor do <code>pTimestamp</code> no atribuito <code>timestamp</code> da Classe.
     *
     * @param pTimestamp Parâmetro de Entrada
     */
    public void setTimestamp(Long pTimestamp) {
        timestamp = pTimestamp;
    }

    /**
     * Obter o valor do atributo <code>noUsuario</code> da classe.
     *
     * @return O atributo <code>noUsuario</code>.
     */
    public String getNoUsuario() {
        return noUsuario;
    }

    /**
     * Atribuir o valor do <code>pNoUsuario</code> no atribuito <code>noUsuario</code> da Classe.
     *
     * @param pNoUsuario Parâmetro de Entrada
     */
    public void setNoUsuario(String pNoUsuario) {
        noUsuario = pNoUsuario;
    }

    /**
     * Obter o valor do atributo <code>nuCpfUsuario</code> da classe.
     *
     * @return O atributo <code>nuCpfUsuario</code>.
     */
    public String getNuCpfUsuario() {
        return nuCpfUsuario;
    }

    /**
     * Atribuir o valor do <code>pNuCpfUsuario</code> no atribuito <code>nuCpfUsuario</code> da Classe.
     *
     * @param pNuCpfUsuario Parâmetro de Entrada
     */
    public void setNuCpfUsuario(String pNuCpfUsuario) {
        nuCpfUsuario = pNuCpfUsuario;
    }

    /**
     * Obter o valor do atributo <code>nuIpUsuario</code> da classe.
     *
     * @return O atributo <code>nuIpUsuario</code>.
     */
    public String getNuIpUsuario() {
        return nuIpUsuario;
    }

    /**
     * Atribuir o valor do <code>pNuIpUsuario</code> no atribuito <code>nuIpUsuario</code> da Classe.
     *
     * @param pNuIpUsuario Parâmetro de Entrada
     */
    public void setNuIpUsuario(String pNuIpUsuario) {
        nuIpUsuario = pNuIpUsuario;
    }

    @Override
    public boolean equals(Object pOhter) {
        return pOhter instanceof HistoricoAcervoAuditoria
                && new EqualsBuilder().append(id, ((HistoricoAcervoAuditoria) pOhter).id)
                .append(timestamp, ((HistoricoAcervoAuditoria) pOhter).timestamp).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(Integer.valueOf("17"), Integer.valueOf("37"))
                .append(id)
                .append(timestamp)
                .toHashCode();
    }

}
