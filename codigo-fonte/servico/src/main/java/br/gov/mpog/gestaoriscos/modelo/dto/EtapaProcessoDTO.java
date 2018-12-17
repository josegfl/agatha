package br.gov.mpog.gestaoriscos.modelo.dto;

public class EtapaProcessoDTO extends EntidadeBaseDTO {

    private ProcessoDTO processo;

    public ProcessoDTO getProcesso() {
        return processo;
    }

    public void setProcesso(ProcessoDTO processo) {
        this.processo = processo;
    }
}
