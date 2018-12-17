package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.modelo.base.ArquivoAnexoBase;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Table(name = "tb_arquivo_anexo")
@Where(clause = "ic_excluido='false'")
@SQLDelete(sql = "UPDATE gestaoriscos.tb_arquivo_anexo SET ic_excluido=true WHERE id_arquivo_anexo=?")
public class ArquivoAnexo extends ArquivoAnexoBase {

    @NotNull
    @Column(name = "fl_conteudo")
    private byte[] arquivo;

    public byte[] getArquivo() {
        return arquivo.clone();
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo.clone();
    }
}
