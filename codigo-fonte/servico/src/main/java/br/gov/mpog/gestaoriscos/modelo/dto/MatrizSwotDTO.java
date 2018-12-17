package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.base.MatrizSwotBase;

public class MatrizSwotDTO extends MatrizSwotBase {

    private TipoMatrizDTO tipoMatriz;

    public TipoMatrizDTO getTipoMatriz() {
        return tipoMatriz;
    }

    public void setTipoMatriz(TipoMatrizDTO tipoMatriz) {
        this.tipoMatriz = tipoMatriz;
    }
}