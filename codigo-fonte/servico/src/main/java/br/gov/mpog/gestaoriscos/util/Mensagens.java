package br.gov.mpog.gestaoriscos.util;

public class Mensagens{

    public static final String MSG_NAO_FOI_POSSIVEL_SALVAR_REGISTRO = "Não foi possível salvar o registro";
    public static final String MSG_NAO_FOI_POSSIVEL_EXCLUIR_REGISTRO = "Não foi possível excluir o registro";
    public static final String MSG_NAO_FOI_POSSIVEL_ENCONTRAR_REGISTRO = "Não foi possível encontrar o registro";

    public static final String MSG_PREENCHA_CAMPOS_OBRIGATORIOS = "Preencha os campos obrigatórios!";

    public static final String OPERACAO_REALIZADA_COM_SUCESSO = "Operação realizada com sucesso!";

    /**
     * US015
     */
    public static final String US015_1 = "Registro já cadastrado.";
    public static final String US015_2 = "Registro salvo com sucesso.";
    public static final String US015_3 = "Registro alterado com sucesso!";
    public static final String US015_4 = "Registro excluído com sucesso!";

    /**
     * US006
     */
    public static final String US006_1 = "Identificação de Eventos de Risco cadastrado com sucesso!";

    /**
     * US009
     */
    public static final String US009_1 = "Avaliação de Riscos e Controles cadastrada com sucesso!";

    /**
     * US014
     */
    public static final String US014_1 = "Resposta a Risco cadastrado com sucesso!";

    /**
     * US0023
     */
    public static final String US023_1 = "Este registro não pode ser alterado!";
    public static final String US023_2 = "Este registro não pode ser excluído!";

    /**
     * US0027
     */
    public static final String US027_1 = "Dados informados, salvos com sucesso!";

    /**
     * US0020
     */
    public static final String US020_1 = "Item não pode ser excluído pois possui vinculação a um Macroprocesso/Processo!";
    public static final String US020_2 = "Preencha o Cálculo de Risco Inerente Corretamente";
    public static final String US020_3 = "Preencha o Controle de Evento de Risco Corretamente";
    public static final String US020_4 = "Preencha o Cálculo de Risco Residual Corretamente";
    public static final String US020_5 = "Item não pode ser excluído pois possui vinculação a uma Taxonomia!";

    /**
     * US0024
     */
    public static String US024_1 = "Plano de Controle cadastrado com sucesso!";

    /**
     * US0026
     */
    public static final String US026_1 = "Sugestão enviada para o Núcleo!";
    public static final String US026_2 = "Taxonomia(s) aprovada(s)!";
    public static final String US026_3 = "Taxonomia(s) reprovada(s)!";

    /**
     * BASIS_18666
     */
    public static final String BASIS_18666_1 = "Este usuário já possui o perfil selecionado!";

    /**
     * US001
     */
    public static final String US001_14 = "Usuário já cadastrado!";

    private Mensagens() {
        throw new IllegalAccessError("Classe Utiliataria");
    }
}
