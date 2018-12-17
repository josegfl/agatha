package br.gov.mpog.gestaoriscos.modelo.base;

import br.gov.mpog.gestaoriscos.modelo.EntidadeBase;
import br.gov.mpog.gestaoriscos.util.AnnotationNumberUtil;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Audited
@MappedSuperclass
public class ProcessoBase extends EntidadeBase {

    @Id
    @Column(name = "id_processo")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_processo", name = "sk_processo")
    @GeneratedValue(generator = "sk_processo", strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = AnnotationNumberUtil.L200, name = "no_processo")
    private String processo;

    @Column(length = AnnotationNumberUtil.L401, name = "ds_search")
    private String search;

    @Column(length = AnnotationNumberUtil.L200, name = "no_diretoria")
    private String diretoria;

    @Column(length = AnnotationNumberUtil.L200, name = "no_coordenacao")
    private String coordenacao;

    @Column(length = AnnotationNumberUtil.L200, name = "ds_objetivo")
    private String objetivo;

    @Column(length = AnnotationNumberUtil.L200, name = "no_lei")
    private String lei;

    @Column(length = AnnotationNumberUtil.L200, name = "no_sistema")
    private String sistema;

    @Column(name = "dt_cadastro", nullable = false)
    private Calendar dtCadastro;

    @Column(name = "dt_inicio")
    private Calendar dtInicio;

    @Column(name = "dt_fim")
    private Calendar dtFim;

    @Column(length = AnnotationNumberUtil.L250, name = "ds_justificativa")
    private String justificativa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getDiretoria() {
        return diretoria;
    }

    public void setDiretoria(String diretoria) {
        this.diretoria = diretoria;
    }

    public String getCoordenacao() {
        return coordenacao;
    }

    public void setCoordenacao(String coordenacao) {
        this.coordenacao = coordenacao;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getLei() {
        return lei;
    }

    public void setLei(String lei) {
        this.lei = lei;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public Calendar getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Calendar dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Calendar getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Calendar dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Calendar getDtFim() {
        return dtFim;
    }

    public void setDtFim(Calendar dtFim) {
        this.dtFim = dtFim;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}