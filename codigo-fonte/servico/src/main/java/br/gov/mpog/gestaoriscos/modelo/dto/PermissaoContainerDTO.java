package br.gov.mpog.gestaoriscos.modelo.dto;

import java.util.List;

public class PermissaoContainerDTO {

    private List<PermissaoDTO> permissaos;

    public List<PermissaoDTO> getPermissaos() {
        return permissaos;
    }

    public void setPermissaos(List<PermissaoDTO> permissaos) {
        this.permissaos = permissaos;
    }
}
