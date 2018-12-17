package br.gov.mpog.gestaoriscos.modelo;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tb_processo_anexo")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_processo_anexo SET ic_excluido=true WHERE id_processo_anexo=?")
public class ProcessoAnexo extends EntidadeBase {

    @Id
    @Column(name = "id_processo_anexo")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_processo_anexo", name = "sk_processo_anexo")
    @GeneratedValue(generator = "sk_processo_anexo", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_processo", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Processo processo;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arquivo_anexo", nullable = false)
    private ArquivoAnexo arquivoAnexo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public ArquivoAnexo getArquivoAnexo() {
        return arquivoAnexo;
    }

    public void setArquivoAnexo(ArquivoAnexo arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo;
    }
}