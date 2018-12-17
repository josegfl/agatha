package br.gov.mpog.gestaoriscos.enums;

public enum CategoriaUnidadeEnum {

    UNIDADE_ADMINISTRATIVA(
            1L,
            "http://estruturaorganizacional.dados.gov.br/id/tipo-unidade/unidade-administrativa"
    ),
    UNIDADE_COLEGIADA(
            2L,
            "http://estruturaorganizacional.dados.gov.br/id/tipo-unidade/unidade-colegiada"
    ),
    ENTIDADE(
            3L,
            "http://estruturaorganizacional.dados.gov.br/id/tipo-unidade/entidade"
    ),
    ORGAO(
            4L,
            "http://estruturaorganizacional.dados.gov.br/id/tipo-unidade/orgao"
    ),

    ENTE(
            5L,
            "http://estruturaorganizacional.dados.gov.br/id/tipo-unidade/ente"
    );

    private Long id;
    private String urlRef;

    CategoriaUnidadeEnum(Long id, String urlRef) {
        this.id = id;
        this.urlRef = urlRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlRef() {
        return urlRef;
    }

    public void setUrlRef(String urlRef) {
        this.urlRef = urlRef;
    }

    public static Long obterIdPorUrlRef(final String urlRef) {
        for (CategoriaUnidadeEnum c : CategoriaUnidadeEnum.values()) {
            if (c.getUrlRef().equalsIgnoreCase(urlRef)) {
                return c.getId();
            }
        }
        return 0L;
    }

}
