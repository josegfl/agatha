package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PerfilDTO {

    private Long id;

    private String nome;

    private Integer prioridade;

    public PerfilDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public PerfilDTO(Long id, String nome, Integer prioridade) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }
}
