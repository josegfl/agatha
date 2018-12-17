package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrgaosDTO{
    private OrgaoDTO orgao;
    private OrgaoDTO secretaria;

    public OrgaoDTO getOrgao(){
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao){
        this.orgao = orgao;
    }

    public OrgaoDTO getSecretaria(){
        return secretaria;
    }

    public void setSecretaria(OrgaoDTO secretaria){
        this.secretaria = secretaria;
    }
}