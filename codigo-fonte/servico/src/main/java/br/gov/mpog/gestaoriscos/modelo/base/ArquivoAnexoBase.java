package br.gov.mpog.gestaoriscos.modelo.base;

import br.gov.mpog.gestaoriscos.modelo.EntidadeBase;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Audited
@MappedSuperclass
public class ArquivoAnexoBase extends EntidadeBase {

    @Id
    @Column(name = "id_arquivo_anexo")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_arquivo_anexo", name = "sk_arquivo_anexo")
    @GeneratedValue(generator = "sk_arquivo_anexo", strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = AnnotationNumberUtil.L200, name = "no_arquivo")
    private String nomeArquivo;

    @Column(length = AnnotationNumberUtil.L200, name = "no_documento")
    private String nomeDocumento;

    @NotNull
    @Column(name = "nu_tamanho")
    private Integer tamanho;

    @NotNull
    @Column(name = "dt_upload")
    private Date dataUpload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataUpload() {
        return (Date) ObjectUtils.cloneIfPossible(dataUpload);
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = (Date) ObjectUtils.cloneIfPossible(dataUpload);
    }
}
