package br.gov.mpog.gestaoriscos.modelo.dto;

import javax.validation.constraints.Size;
import java.util.List;

public class EventoRiscoDTO {

    private Long id;

    private boolean excluido;

    private IdentificacaoDTO identificacao;

    private EventoDTO evento;

    private CategoriaDTO categoria;

    private List<EventoCausaDTO> causas;

    private List<EventoConsequenciaDTO> consequencias;

    private List<ControleEventoDTO> controleEventos;

    private CalculoRiscoDTO calculoRiscoInerente;

    private CalculoRiscoDTO calculoRiscoResidual;

    @Size(max = 4000)
    private String justificativaRespostaRisco;

    private RespostaRiscoDTO respostaRisco;

    private List<PlanoControleDTO> controles;

    private String cpf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public IdentificacaoDTO getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(IdentificacaoDTO identificacao) {
        this.identificacao = identificacao;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public List<EventoCausaDTO> getCausas() {
        return causas;
    }

    public void setCausas(List<EventoCausaDTO> causas) {
        this.causas = causas;
    }

    public List<EventoConsequenciaDTO> getConsequencias() {
        return consequencias;
    }

    public void setConsequencias(List<EventoConsequenciaDTO> consequencias) {
        this.consequencias = consequencias;
    }

    public List<ControleEventoDTO> getControleEventos() {
        return controleEventos;
    }

    public void setControleEventos(List<ControleEventoDTO> controleEventos) {
        this.controleEventos = controleEventos;
    }

    public CalculoRiscoDTO getCalculoRiscoInerente() {
        return calculoRiscoInerente;
    }

    public void setCalculoRiscoInerente(CalculoRiscoDTO calculoRiscoInerente) {
        this.calculoRiscoInerente = calculoRiscoInerente;
    }

    public CalculoRiscoDTO getCalculoRiscoResidual() {
        return calculoRiscoResidual;
    }

    public void setCalculoRiscoResidual(CalculoRiscoDTO calculoRiscoResidual) {
        this.calculoRiscoResidual = calculoRiscoResidual;
    }

    public String getJustificativaRespostaRisco() {
        return justificativaRespostaRisco;
    }

    public void setJustificativaRespostaRisco(String justificativaRespostaRisco) {
        this.justificativaRespostaRisco = justificativaRespostaRisco;
    }

    public RespostaRiscoDTO getRespostaRisco() {
        return respostaRisco;
    }

    public void setRespostaRisco(RespostaRiscoDTO respostaRisco) {
        this.respostaRisco = respostaRisco;
    }

    public List<PlanoControleDTO> getControles() {
        return controles;
    }

    public void setControles(List<PlanoControleDTO> controles) {
        this.controles = controles;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}