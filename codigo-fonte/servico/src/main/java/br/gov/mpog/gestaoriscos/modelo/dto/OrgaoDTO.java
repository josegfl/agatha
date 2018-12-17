package br.gov.mpog.gestaoriscos.modelo.dto;

import br.gov.mpog.gestaoriscos.modelo.CategoriaUnidade;
import br.gov.mpog.gestaoriscos.modelo.NaturezaJuridica;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.base.EntidadeBaseOrgao;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class OrgaoDTO extends EntidadeBaseOrgao {

    private OrgaoDTO orgaoPai;
    private List<OrgaoDTO> orgaosFilhos = new LinkedList<>();
    private List<UsuarioDTO> usuarios = new LinkedList<>();

    private CategoriaUnidadeDTO categoriaUnidade;
    private NaturezaJuridicaDTO naturezaJuridica;

    private Long idCategoriaUnidade;
    private Short idNaturezaJuridica;

    public OrgaoDTO(Orgao orgao) {
        Optional.ofNullable(orgao).ifPresent(orgaoAux -> {
            setId(orgaoAux.getId());
            setNome(orgaoAux.getNome());
            setSigla(orgaoAux.getSigla());

            Optional.ofNullable(orgaoAux.getUsuarios()).
                    filter(usuarios -> !usuarios.isEmpty())
                    .ifPresent(usuarios -> usuarios.forEach(usuario -> {
                                this.usuarios.add(new UsuarioDTO(usuario));
                            })
                    );

            //-> Categoria e Natureza Juridica
            this.categoriaUnidade = new CategoriaUnidadeDTO(Optional.ofNullable(orgaoAux.getCategoriaUnidade()).orElse(new CategoriaUnidade()));
            this.naturezaJuridica = new NaturezaJuridicaDTO(Optional.ofNullable(orgaoAux.getNaturezaJuridica()).orElse(new NaturezaJuridica()));

            //-> Adiciona apenas dados simples do PAI para não entrar em LOOP
            Optional.ofNullable(orgaoAux.getOrgaoPai()).ifPresent(orgaoPai -> {
                this.orgaoPai = new OrgaoDTO(orgaoPai.getId(), orgaoPai.getNome(), orgaoPai.getSigla(),
                        orgaoPai.getIdCategoriaUnidade(), orgaoPai.getIdNaturezaJuridica());
            });
            //-> Adiciona o filho completo para dar o LOOP e adicionar os filhos dos filhos
            Optional.ofNullable(orgaoAux.getOrgaosFilhos())
                    .filter(orgaosFilhos -> !orgaosFilhos.isEmpty())
                    .ifPresent(orgaosFilhos -> orgaosFilhos.forEach(
                            orgaoFilho -> this.orgaosFilhos.add(new OrgaoDTO(orgaoFilho))
                            )
                    );
        });
    }

    public OrgaoDTO(Long id, String noOrgao, String siglaOrgao, Long idCategoriaUnidade, Short idNaturezaJuridica) {
        setId(id);
        setNome(noOrgao);
        setSigla(siglaOrgao);

        //-> Categoria e Natureza Juridica
        this.categoriaUnidade = new CategoriaUnidadeDTO(idCategoriaUnidade);
        this.naturezaJuridica = new NaturezaJuridicaDTO(idNaturezaJuridica);
    }

    public OrgaoDTO(Long id, String nome, String sigla) {
        super();
        setId(id);
        setNome(nome);
        setSigla(sigla);
    }

    public OrgaoDTO getOrgaoPai() {
        return orgaoPai;
    }

    public void setOrgaoPai(OrgaoDTO orgaoPai) {
        this.orgaoPai = orgaoPai;
    }

    public List<OrgaoDTO> getOrgaosFilhos() {
        return orgaosFilhos;
    }

    public void setOrgaosFilhos(List<OrgaoDTO> orgaosFilhos) {
        this.orgaosFilhos = orgaosFilhos;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public CategoriaUnidadeDTO getCategoriaUnidade() {
        return categoriaUnidade;
    }

    public void setCategoriaUnidade(CategoriaUnidadeDTO categoriaUnidade) {
        this.categoriaUnidade = categoriaUnidade;
    }

    public NaturezaJuridicaDTO getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(NaturezaJuridicaDTO naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public Long getIdCategoriaUnidade() {
        return idCategoriaUnidade;
    }

    public void setIdCategoriaUnidade(Long idCategoriaUnidade) {
        this.idCategoriaUnidade = idCategoriaUnidade;
    }

    public Short getIdNaturezaJuridica() {
        return idNaturezaJuridica;
    }

    public void setIdNaturezaJuridica(Short idNaturezaJuridica) {
        this.idNaturezaJuridica = idNaturezaJuridica;
    }
}