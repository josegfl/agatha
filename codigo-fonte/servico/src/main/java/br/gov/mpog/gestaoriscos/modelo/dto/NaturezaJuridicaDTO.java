package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.NaturezaJuridica;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NaturezaJuridicaDTO {
    private Short id;
    private String nome;

    public NaturezaJuridicaDTO(Short id) {
        this.id = id;
    }

    public NaturezaJuridicaDTO(NaturezaJuridica naturezaJuridica) {
        this(naturezaJuridica.getId());
        this.nome = naturezaJuridica.getNome();
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}