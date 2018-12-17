package br.gov.mpog.gestaoriscos.modelo.dto;

/**
 * Created by luizfernando on 22/06/17.
 */
public class TaxonomiaOrgaoBaseDTO extends EntidadeBaseDescricaoStatusSearchDTO {

    private OrgaoDTO orgao;

    public OrgaoDTO getOrgao() {
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao) {
        this.orgao = orgao;
    }
}
