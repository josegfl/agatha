package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;

import java.util.List;

public interface PerfilService {

    List<PerfilDTO> findByCPF(String cpf);

}
