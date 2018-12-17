package br.gov.mpog.gestaoriscos.modelo.dto;

public class ProcessoAnexoDTO{

    private Long id;

    private boolean excluido;

    private ArquivoAnexoDTO arquivoAnexo;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public boolean isExcluido(){
        return excluido;
    }

    public void setExcluido(boolean excluido){
        this.excluido = excluido;
    }

    public ArquivoAnexoDTO getArquivoAnexo(){
        return arquivoAnexo;
    }

    public void setArquivoAnexo(ArquivoAnexoDTO arquivoAnexo){
        this.arquivoAnexo = arquivoAnexo;
    }
}