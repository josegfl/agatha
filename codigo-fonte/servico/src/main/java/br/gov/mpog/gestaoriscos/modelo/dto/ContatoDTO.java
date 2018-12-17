package br.gov.mpog.gestaoriscos.modelo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ContatoDTO {
    private List<String> email;
    private List<String> telefone;
    private List<WebsiteDTO> site;
}
