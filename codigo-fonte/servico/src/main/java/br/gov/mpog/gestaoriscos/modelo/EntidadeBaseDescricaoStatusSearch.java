package br.gov.mpog.gestaoriscos.modelo;

import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Audited
@MappedSuperclass
public class EntidadeBaseDescricaoStatusSearch extends EntidadeBaseDescricaoStatus {

    @Column(length = AnnotationNumberUtil.L1000, name = "ds_search")
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
