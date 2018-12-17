package br.gov.mpog.gestaoriscos.modelo.base;

import br.gov.mpog.gestaoriscos.modelo.EntidadeBaseDescricaoStatus;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Audited
@MappedSuperclass
public class GlossarioBase extends EntidadeBaseDescricaoStatus {

    @Column(length = AnnotationNumberUtil.L200, name = "no_glossario")
    private String termo;

    @Column(length = AnnotationNumberUtil.L200, name = "ds_termo_search")
    private String termoSearch;

    @Column(length = AnnotationNumberUtil.L1000, name = "ds_descricao_search")
    private String descricaoSearch;

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getTermoSearch() {
        return termoSearch;
    }

    public void setTermoSearch(String termoSearch) {
        this.termoSearch = termoSearch;
    }

    public String getDescricaoSearch() {
        return descricaoSearch;
    }

    public void setDescricaoSearch(String descricaoSearch) {
        this.descricaoSearch = descricaoSearch;
    }
}