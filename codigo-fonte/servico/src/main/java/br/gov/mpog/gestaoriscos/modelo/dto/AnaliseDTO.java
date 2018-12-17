package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.base.AnaliseBase;

import java.util.List;

public class AnaliseDTO extends AnaliseBase {

    private OrgaoDTO orgao;

    private OrgaoDTO secretaria;

    private List<MatrizSwotDTO> matrizes;

    private ProcessoDTO processo;

    public OrgaoDTO getOrgao() {
        return orgao;
    }

    public void setOrgao(OrgaoDTO orgao) {
        if (this.orgao != null) {
            this.orgao.setOrgaoPai(null);
            this.orgao.setOrgaosFilhos(null);
            this.orgao.setUsuarios(null);
        }
        this.orgao = orgao;
    }

    public OrgaoDTO getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(OrgaoDTO secretaria) {
        if (this.secretaria != null) {
            this.secretaria.setOrgaoPai(null);
            this.secretaria.setOrgaosFilhos(null);
            this.secretaria.setUsuarios(null);
        }
        this.secretaria = secretaria;
    }

    public List<MatrizSwotDTO> getMatrizes() {
        return matrizes;
    }

    public void setMatrizes(List<MatrizSwotDTO> matrizes) {
        this.matrizes = matrizes;
    }

    public ProcessoDTO getProcesso() {
        return processo;
    }

    public void setProcesso(ProcessoDTO processo) {
        this.processo = processo;
    }
}