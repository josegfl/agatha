package br.gov.mpog.gestaoriscos.modelo.dto;

public class ResponsavelDTO{

    private Long id;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;

    private boolean excluido;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public ProcessoDTO getProcesso(){
        return processo;
    }

    public void setProcesso(ProcessoDTO processo){
        this.processo = processo;
    }

    public UsuarioDTO getUsuario(){
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario){
        this.usuario = usuario;
    }

    public boolean isExcluido(){
        return excluido;
    }

    public void setExcluido(boolean excluido){
        this.excluido = excluido;
    }
}