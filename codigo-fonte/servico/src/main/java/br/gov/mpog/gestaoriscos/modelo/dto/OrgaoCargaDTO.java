package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.enums.CategoriaUnidadeEnum;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.Optional.ofNullable;

@Setter
@Getter
@NoArgsConstructor
public class OrgaoCargaDTO implements Comparable {
    private AtoNormativoDTO atoNormativo;
    private String codigoCategoriaUnidade;
    private String codigoEsfera;
    private String codigoNaturezaJuridica;
    private String codigoOrgaoEntidade;
    private String codigoPoder;
    private String codigoSubNaturezaJuridica;
    private String codigoTipoUnidade;
    private String codigoUnidade;
    private String codigoUnidadePai;
    private String competencia;
    private List<ContatoDTO> contato;
    private String dataFinalVersaoConsulta;
    private String dataInicialVersaoConsulta;
    private String descricaoAtoNormativo;
    private List<EnderecoDTO> endereco;
    private String finalidade;
    private String missao;
    private String nivelNormatizacao;
    private String nome;
    private String operacao;
    private String sigla;
    private String versaoConsulta;

    private int nivel;

    private OrgaoCargaDTO pai;

    private Long toLong(String value) {
        if (value == null || !StringUtils.hasText(value.trim())) {
            return null;
        }
        return Long.valueOf(ofNullable(value).map(s -> s.replaceAll("^.*?(\\d+)$", "$1")).orElse("0"));
    }

    public Long getCodigoUnidadeAsLong() {
        return toLong(codigoUnidade);
    }

    public Long getCodigoUnidadePaiAsLong() {
        Long id = toLong(codigoUnidadePai);
        if (id == null) {
            return null;
        }
        return id;
    }

    public Orgao orgaoSemPai() {
        return orgao(false);
    }

    public Orgao orgao() {
        return orgao(true);
    }

    private Orgao orgao(final boolean comPai) {
        Long orgaoPaiId = comPai ? this.getCodigoUnidadePaiAsLong() : null;
        Orgao orgao = new Orgao(
                this.getCodigoUnidadeAsLong(),
                this.nome,
                this.sigla,
                orgaoPaiId,
                getCodigoCategoriaUnidadeAsLong(),
                getCodigoNaturezaJuridicaAsShort()
        );
        return orgao;
    }

    public Long getCodigoOrgaoEntidadeAsLong() {
        return toLong(codigoOrgaoEntidade);
    }

    //-> FIXME: Considerado como codigoTipoUnidade
    public Long getCodigoCategoriaUnidadeAsLong() {
        return CategoriaUnidadeEnum.obterIdPorUrlRef(codigoTipoUnidade);
    }

    public Long getCodigoNaturezaJuridicaAsLong() {
        return toLong(codigoNaturezaJuridica);
    }

    public Short getCodigoNaturezaJuridicaAsShort() {
        Long id = toLong(codigoNaturezaJuridica);
        if (id == null) {
            return null;
        }
        return id.shortValue();
    }

    public Long getCodigoSubNaturezaJuridicaAsInt() {
        return toLong(codigoSubNaturezaJuridica);
    }

    public void definirNivel() {
        this.nivel = 0;
        OrgaoCargaDTO pai = getPai();
        while (true) {
            if (pai != null) {
                this.nivel++;
                pai = pai.getPai();
            } else {
                break;
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        OrgaoCargaDTO outro = (OrgaoCargaDTO) o;
        if (outro.getNivel() > this.getNivel()) {
            return -1;
        } else if (outro.getNivel() == this.getNivel()) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgaoCargaDTO that = (OrgaoCargaDTO) o;

        return getNivel() == that.getNivel();
    }

    @Override
    public int hashCode() {
        return getNivel();
    }

}
