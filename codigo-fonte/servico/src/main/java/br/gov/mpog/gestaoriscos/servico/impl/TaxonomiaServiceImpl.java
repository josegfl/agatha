package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.StatusTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.TipoTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaContainerDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.repositorio.CausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.servico.TaxonomiaService;
import br.gov.mpog.gestaoriscos.servico.mapper.OrgaoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TaxonomiaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoTaxonomiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Taxonomia.
 */
@Service
@Transactional
public class TaxonomiaServiceImpl implements TaxonomiaService {

    private final Logger log = LoggerFactory.getLogger(TaxonomiaServiceImpl.class);

    private final TaxonomiaRepository taxonomiaRepository;

    private final TipoTaxonomiaRepository tipoTaxonomiaRepository;

    private final StatusTaxonomiaRepository statusTaxonomiaRepository;

    private final TaxonomiaCustomRepositorio taxonomiaCustomRepositorio;

    private final OrgaoCustomRepositorio orgaoCustomRepositorio;

    private final EventoRepository eventoRepository;

    private final CausaRepository causaRepository;

    private final ConsequenciaRepository consequenciaRepository;

    private final ControleRepository controleRepository;

    private final TaxonomiaMapper taxonomiaMapper;

    private final TipoTaxonomiaMapper tipoTaxonomiaMapper;

    private final PermissaoRepository permissaoRepository;

    private final OrgaoRepository orgaoRepository;

    private final OrgaoMapper orgaoMapper;

    @Autowired
    public TaxonomiaServiceImpl(TaxonomiaRepository taxonomiaRepository, TipoTaxonomiaRepository tipoTaxonomiaRepository, StatusTaxonomiaRepository statusTaxonomiaRepository, TaxonomiaCustomRepositorio taxonomiaCustomRepositorio, OrgaoCustomRepositorio orgaoCustomRepositorio, EventoRepository eventoRepository, CausaRepository causaRepository, ConsequenciaRepository consequenciaRepository, ControleRepository controleRepository, TaxonomiaMapper taxonomiaMapper, TipoTaxonomiaMapper tipoTaxonomiaMapper, PermissaoRepository permissaoRepository, OrgaoRepository orgaoRepository, OrgaoMapper orgaoMapper) {
        this.taxonomiaRepository = taxonomiaRepository;
        this.tipoTaxonomiaRepository = tipoTaxonomiaRepository;
        this.statusTaxonomiaRepository = statusTaxonomiaRepository;
        this.taxonomiaCustomRepositorio = taxonomiaCustomRepositorio;
        this.orgaoCustomRepositorio = orgaoCustomRepositorio;
        this.eventoRepository = eventoRepository;
        this.causaRepository = causaRepository;
        this.consequenciaRepository = consequenciaRepository;
        this.controleRepository = controleRepository;
        this.taxonomiaMapper = taxonomiaMapper;
        this.tipoTaxonomiaMapper = tipoTaxonomiaMapper;
        this.permissaoRepository = permissaoRepository;
        this.orgaoRepository = orgaoRepository;
        this.orgaoMapper = orgaoMapper;
    }

    @Override
    public void aprovarTaxonomia(TaxonomiaContainerDTO containerDTO) {
        StatusTaxonomia status = statusTaxonomiaRepository.findByNomeIgnoreCase("Aprovado");
        List<Taxonomia> taxonomias = taxonomiaMapper.taxonomiaDTOsToTaxonomias(containerDTO.getTaxonomias());

        for (Taxonomia taxonomia : taxonomias) {
            taxonomia = taxonomiaRepository.findOne(taxonomia.getId());
            taxonomia.setStatus(status);

            taxonomia = taxonomiaRepository.save(taxonomia);

            if(taxonomia.getTipo().getNome().equals("Evento")) {
                Evento evento = taxonomia.getEvento();
                evento.setOrgao(null);
                eventoRepository.save(evento);

            }
            if(taxonomia.getTipo().getNome().equals("Causa")) {
                Causa causa = taxonomia.getCausa();
                causa.setOrgao(null);
                causaRepository.save(causa);

            }
            if(taxonomia.getTipo().getNome().equals("Consequência")) {
                Consequencia consequencia = taxonomia.getConsequencia();
                consequencia.setOrgao(null);
                consequenciaRepository.save(consequencia);

            }
            if(taxonomia.getTipo().getNome().equals("Controle")) {
                Controle controle = taxonomia.getControle();
                controle.setOrgao(null);
                controleRepository.save(controle);

            }
        }
    }

    @Override
    public void reprovarTaxonomia(TaxonomiaContainerDTO containerDTO) {
        StatusTaxonomia status = statusTaxonomiaRepository.findByNomeIgnoreCase("Reprovado");
        List<Taxonomia> taxonomias = taxonomiaMapper.taxonomiaDTOsToTaxonomias(containerDTO.getTaxonomias());

        for (Taxonomia taxonomia : taxonomias) {
            taxonomia = taxonomiaRepository.findOne(taxonomia.getId());
            taxonomia.setStatus(status);
            taxonomia.setJustificativa(containerDTO.getJustificativa());

            taxonomiaRepository.save(taxonomia);
        }
    }

    /**
     * Get all the taxonomias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaxonomiaDTO> findAll(Pageable pageable, String descricao, String orgao, Long inicio, Long fim, Long tipoTaxonomiaId) {
        log.debug("Request to get all Taxonomias");
        StatusTaxonomia status = statusTaxonomiaRepository.findByNomeIgnoreCase("Não avaliado");
        Page<Taxonomia> result = taxonomiaCustomRepositorio.listarTaxonomias(pageable, descricao, orgao, inicio, fim, tipoTaxonomiaId, status.getId());
        return result.map(taxonomiaMapper::taxonomiaToTaxonomiaDTO);
    }

    @Override
    public List<TipoTaxonomiaDTO> findAllTiposTaxonomia() {
        List<TipoTaxonomia> result = tipoTaxonomiaRepository.findAll();
        return tipoTaxonomiaMapper.tipoTaxonomiasToTipoTaxonomiaDTOs(result);
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaCustomRepositorio.searchByDescricao(descricao, false);
    }

    @Override
    public List<String> searchOrgaoByNome(String nome) {
        return orgaoCustomRepositorio.searchByNomeAndOrgaoPaiId(nome, 2981L);
    }

    @Override
    public OrgaoDTO getSecretariaByPerfil(String cpf) {
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Analista de Risco");
        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Gestor do Processo");

        if (analista != null || gestor != null) {
            Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
            return orgaoMapper.orgaoToOrgaoDTO(orgao);
        } else {
            return null;
        }
    }
}