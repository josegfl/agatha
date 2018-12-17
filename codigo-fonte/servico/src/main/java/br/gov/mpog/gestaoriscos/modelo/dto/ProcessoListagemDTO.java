package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.Responsavel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@Data
public class ProcessoListagemDTO {

    private Long id;

    private Calendar dtCadastro;

    private String macroprocesso;

    private String processo;

    private String status;

    private Boolean isGestor;

    private Boolean isAnalista;

    public ProcessoListagemDTO(Processo processo, String cpf) {
        this.id = processo.getId();
        this.dtCadastro = processo.getDtCadastro();
        this.macroprocesso = processo.getMacroprocesso() != null ? processo.getMacroprocesso().getDescricao() : "";
        this.processo = processo.getProcesso() != null ? processo.getProcesso() : "";
        this.status = processo.getStatus().getNome();

        this.isGestor = cpf.equals(processo.getGestor().getCpf());

        this.isAnalista = false;

        for (Responsavel responsavel : processo.getResponsaveis()) {
            this.isAnalista = !this.isAnalista ? cpf.equals(responsavel.getUsuario().getCpf()) : this.isAnalista;
        }
    }

}
