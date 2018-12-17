package br.gov.mpog.gestaoriscos.modelo.base;

import br.gov.mpog.gestaoriscos.modelo.EntidadeBase;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@Audited
@MappedSuperclass
public class AnaliseBase extends EntidadeBase {

    @Id
    @Column(name = "id_analise")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "sk_analise", name = "sk_analise")
    @GeneratedValue(generator = "sk_analise", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ic_etica", nullable = false)
    private Boolean etica;

    @Column(name = "ic_estrutura", nullable = false)
    private Boolean estrutura;

    @Column(name = "ic_recursos_humanos", nullable = false)
    private Boolean recursosHumanos;

    @Column(name = "ic_atribuicoes", nullable = false)
    private Boolean atribuicoes;

    @Column(name = "ic_normas_internas", nullable = false)
    private Boolean normasInternas;

    @Column(name = "ic_missao", nullable = false)
    private Boolean missao;

    @Column(name = "ic_visao", nullable = false)
    private Boolean visao;

    @Column(name = "ic_objetivos", nullable = false)
    private Boolean objetivos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEtica() {
        return etica;
    }

    public void setEtica(Boolean etica) {
        this.etica = etica;
    }

    public Boolean getEstrutura() {
        return estrutura;
    }

    public void setEstrutura(Boolean estrutura) {
        this.estrutura = estrutura;
    }

    public Boolean getRecursosHumanos() {
        return recursosHumanos;
    }

    public void setRecursosHumanos(Boolean recursosHumanos) {
        this.recursosHumanos = recursosHumanos;
    }

    public Boolean getAtribuicoes() {
        return atribuicoes;
    }

    public void setAtribuicoes(Boolean atribuicoes) {
        this.atribuicoes = atribuicoes;
    }

    public Boolean getNormasInternas() {
        return normasInternas;
    }

    public void setNormasInternas(Boolean normasInternas) {
        this.normasInternas = normasInternas;
    }

    public Boolean getMissao() {
        return missao;
    }

    public void setMissao(Boolean missao) {
        this.missao = missao;
    }

    public Boolean getVisao() {
        return visao;
    }

    public void setVisao(Boolean visao) {
        this.visao = visao;
    }

    public Boolean getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(Boolean objetivos) {
        this.objetivos = objetivos;
    }
}