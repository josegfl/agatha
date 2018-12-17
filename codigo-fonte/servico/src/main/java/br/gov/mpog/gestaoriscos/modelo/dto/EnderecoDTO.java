package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO {
    private String bairro;
    private String cep;
    private String complemento;
    private String horarioDeFuncionamento;
    private String logradouro;
    private String municipio;
    private int numero;
    private int pais;
    private String tipoEndereco;
    private String uf;
}
