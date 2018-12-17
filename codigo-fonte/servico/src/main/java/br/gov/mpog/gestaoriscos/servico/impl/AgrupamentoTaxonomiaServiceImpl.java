package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.AgrupamentoTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.modelo.EventoCausa;
import br.gov.mpog.gestaoriscos.modelo.EventoConsequencia;
import br.gov.mpog.gestaoriscos.modelo.EventoRisco;
import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.StatusTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.TipoTaxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.AgrupamentoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.repositorio.AgrupamentoTaxonomiaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.AgrupamentoTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.CausaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.CausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ControleEventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoCausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.EventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PlanoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.servico.AgrupamentoTaxonomiaService;
import br.gov.mpog.gestaoriscos.servico.mapper.AgrupamentoTaxonomiaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CausaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ConsequenciaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TaxonomiaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoTaxonomiaMapper;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Service Implementation for managing Taxonomia.
 */
@Service
@Transactional
public class AgrupamentoTaxonomiaServiceImpl implements AgrupamentoTaxonomiaService {

    private final Logger log = LoggerFactory.getLogger(AgrupamentoTaxonomiaServiceImpl.class);

    private final AgrupamentoTaxonomiaRepository agrupamentoTaxonomiaRepository;

    private final TaxonomiaRepository taxonomiaRepository;

    private final StatusTaxonomiaRepository statusTaxonomiaRepository;

    private final TipoTaxonomiaRepository tipoTaxonomiaRepository;

    private final AgrupamentoTaxonomiaCustomRepositorio agrupamentoTaxonomiaCustomRepositorio;

    private final TaxonomiaCustomRepositorio taxonomiaCustomRepositorio;

    private final EventoRepository eventoRepository;

    private final CausaRepository causaRepository;

    private final ConsequenciaRepository consequenciaRepository;

    private final ControleRepository controleRepository;

    private final OrgaoRepository orgaoRepository;

    private final EventoRiscoRepository eventoRiscoRepository;

    private final EventoCausaRepository eventoCausaRepository;

    private final EventoConsequenciaRepository eventoConsequenciaRepository;

    private final ControleEventoRepository controleEventoRepository;

    private final PlanoControleRepository planoControleRepository;

    private final EventoCustomRepositorio eventoCustomRepositorio;

    private final CausaCustomRepositorio causaCustomRepositorio;

    private final ConsequenciaCustomRepositorio consequenciaCustomRepositorio;

    private final ControleCustomRepositorio controleCustomRepositorio;

    private final AgrupamentoTaxonomiaMapper agrupamentoTaxonomiaMapper;

    private final TipoTaxonomiaMapper tipoTaxonomiaMapper;

    private final TaxonomiaMapper taxonomiaMapper;

    private final EventoMapper eventoMapper;

    private final CausaMapper causaMapper;

    private final ConsequenciaMapper consequenciaMapper;

    private final ControleMapper controleMapper;

    @Autowired
    public AgrupamentoTaxonomiaServiceImpl(AgrupamentoTaxonomiaRepository agrupamentoTaxonomiaRepository, CausaRepository causaRepository, TaxonomiaRepository taxonomiaRepository, StatusTaxonomiaRepository statusTaxonomiaRepository, TipoTaxonomiaRepository tipoTaxonomiaRepository, AgrupamentoTaxonomiaCustomRepositorio agrupamentoTaxonomiaCustomRepositorio, TaxonomiaCustomRepositorio taxonomiaCustomRepositorio, ControleCustomRepositorio controleCustomRepositorio, EventoRepository eventoRepository, PlanoControleRepository planoControleRepository, EventoCausaRepository eventoCausaRepository, ConsequenciaRepository consequenciaRepository, AgrupamentoTaxonomiaMapper agrupamentoTaxonomiaMapper, TaxonomiaMapper taxonomiaMapper, ControleRepository controleRepository, EventoCustomRepositorio eventoCustomRepositorio, ConsequenciaCustomRepositorio consequenciaCustomRepositorio, EventoConsequenciaRepository eventoConsequenciaRepository, OrgaoRepository orgaoRepository, ControleEventoRepository controleEventoRepository, EventoRiscoRepository eventoRiscoRepository, CausaCustomRepositorio causaCustomRepositorio, TipoTaxonomiaMapper tipoTaxonomiaMapper, EventoMapper eventoMapper, CausaMapper causaMapper, ConsequenciaMapper consequenciaMapper, ControleMapper controleMapper) {
        this.agrupamentoTaxonomiaRepository = agrupamentoTaxonomiaRepository;
        this.causaRepository = causaRepository;
        this.taxonomiaRepository = taxonomiaRepository;
        this.statusTaxonomiaRepository = statusTaxonomiaRepository;
        this.tipoTaxonomiaRepository = tipoTaxonomiaRepository;
        this.agrupamentoTaxonomiaCustomRepositorio = agrupamentoTaxonomiaCustomRepositorio;
        this.taxonomiaCustomRepositorio = taxonomiaCustomRepositorio;
        this.controleCustomRepositorio = controleCustomRepositorio;
        this.eventoRepository = eventoRepository;
        this.planoControleRepository = planoControleRepository;
        this.eventoCausaRepository = eventoCausaRepository;
        this.consequenciaRepository = consequenciaRepository;
        this.agrupamentoTaxonomiaMapper = agrupamentoTaxonomiaMapper;
        this.taxonomiaMapper = taxonomiaMapper;
        this.controleRepository = controleRepository;
        this.eventoCustomRepositorio = eventoCustomRepositorio;
        this.consequenciaCustomRepositorio = consequenciaCustomRepositorio;
        this.eventoConsequenciaRepository = eventoConsequenciaRepository;
        this.orgaoRepository = orgaoRepository;
        this.controleEventoRepository = controleEventoRepository;
        this.eventoRiscoRepository = eventoRiscoRepository;
        this.causaCustomRepositorio = causaCustomRepositorio;
        this.tipoTaxonomiaMapper = tipoTaxonomiaMapper;
        this.eventoMapper = eventoMapper;
        this.causaMapper = causaMapper;
        this.consequenciaMapper = consequenciaMapper;
        this.controleMapper = controleMapper;
    }

    @Override
    public AgrupamentoTaxonomiaDTO save(AgrupamentoTaxonomiaDTO agrupamentoTaxonomiaDTO) {
        AgrupamentoTaxonomia agrupamentoTaxonomia = agrupamentoTaxonomiaMapper.agrupamentoTaxonomiaDTOToAgrupamentoTaxonomia(agrupamentoTaxonomiaDTO);

        Taxonomia taxonomia = agrupamentoTaxonomia.getTaxonomia();

        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase("Aprovado"));
        taxonomia.setSearch(StringUtil.removerAcento(taxonomia.getDescricao()));
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setTipo(agrupamentoTaxonomia.getTaxonomias().get(0).getTipo());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(agrupamentoTaxonomiaDTO.getCpf()));

        if(agrupamentoTaxonomia.getTaxonomias().get(0).getTipo().getNome().equals("Evento")) {
            Evento evento;

            if (taxonomia.getEvento() != null && taxonomia.getEvento().getId() != null) {
                evento = eventoRepository.findOne(taxonomia.getEvento().getId());
                if (!evento.getDescricao().equalsIgnoreCase(taxonomia.getEvento().getDescricao())) {
                    evento = saveEvento(taxonomia.getDescricao());
                } else if (!evento.getDescricao().equalsIgnoreCase(taxonomia.getDescricao())) {
                    evento.setDescricao(taxonomia.getDescricao());
                    evento.setSearch(taxonomia.getSearch());
                    evento = eventoRepository.save(evento);
                }
            } else {
                evento = saveEvento(taxonomia.getDescricao());
            }

            taxonomia.setEvento(evento);

        }
        if(agrupamentoTaxonomia.getTaxonomias().get(0).getTipo().getNome().equals("Causa")) {
            Causa causa;

            if (taxonomia.getCausa() != null && taxonomia.getCausa().getId() != null) {
                causa = causaRepository.findOne(taxonomia.getCausa().getId());
                if (!causa.getDescricao().equalsIgnoreCase(taxonomia.getCausa().getDescricao())) {
                    causa = saveCausa(taxonomia.getDescricao());
                } else if (!causa.getDescricao().equalsIgnoreCase(taxonomia.getDescricao())) {
                    causa.setDescricao(taxonomia.getDescricao());
                    causa.setSearch(taxonomia.getSearch());
                    causa = causaRepository.save(causa);
                }
            } else {
                causa = saveCausa(taxonomia.getDescricao());
            }

            taxonomia.setCausa(causa);

        }
        if(agrupamentoTaxonomia.getTaxonomias().get(0).getTipo().getNome().equals("Consequência")) {
            Consequencia consequencia;

            if (taxonomia.getConsequencia() != null && taxonomia.getConsequencia().getId() != null) {
                consequencia = consequenciaRepository.findOne(taxonomia.getConsequencia().getId());
                if (!consequencia.getDescricao().equalsIgnoreCase(taxonomia.getConsequencia().getDescricao())) {
                    consequencia = saveConsequencia(taxonomia.getDescricao());
                } else if (!consequencia.getDescricao().equalsIgnoreCase(taxonomia.getDescricao())) {
                    consequencia.setDescricao(taxonomia.getDescricao());
                    consequencia.setSearch(taxonomia.getSearch());
                    consequencia = consequenciaRepository.save(consequencia);
                }
            } else {
                consequencia = saveConsequencia(taxonomia.getDescricao());
            }

            taxonomia.setConsequencia(consequencia);

        }
        if(agrupamentoTaxonomia.getTaxonomias().get(0).getTipo().getNome().equals("Controle")) {
            Controle controle;

            if (taxonomia.getControle() != null && taxonomia.getControle().getId() != null) {
                controle = controleRepository.findOne(taxonomia.getControle().getId());
                if (!controle.getDescricao().equalsIgnoreCase(taxonomia.getControle().getDescricao())) {
                    controle = saveControle(taxonomia.getDescricao());
                } else if (!controle.getDescricao().equalsIgnoreCase(taxonomia.getDescricao())) {
                    controle.setDescricao(taxonomia.getDescricao());
                    controle.setSearch(taxonomia.getSearch());
                    controle = controleRepository.save(controle);
                }
            } else {
                controle = saveControle(taxonomia.getDescricao());
            }

            taxonomia.setControle(controle);
        }

        List<Taxonomia> taxonomias = agrupamentoTaxonomia.getTaxonomias();
        agrupamentoTaxonomia.setTaxonomias(null);

        agrupamentoTaxonomia.setTaxonomia(taxonomia);
        agrupamentoTaxonomia = agrupamentoTaxonomiaRepository.save(agrupamentoTaxonomia);

        StatusTaxonomia statusAgrupado = statusTaxonomiaRepository.findByNomeIgnoreCase("Agrupado");

        for (Taxonomia taxonomiaSelecionada : taxonomias) {
            taxonomiaSelecionada.setAgrupamento(agrupamentoTaxonomia);
            taxonomiaSelecionada.setStatus(statusAgrupado);

            if(taxonomiaSelecionada.getTipo().getNome().equals("Evento")) {
                List<EventoRisco> eventoRiscos = eventoRiscoRepository.findByEventoId(taxonomiaSelecionada.getEvento().getId());
                for (EventoRisco eventoRisco : eventoRiscos) {
                    eventoRisco.setEvento(taxonomia.getEvento());
                }
                eventoRiscoRepository.save(eventoRiscos);
            }
            if(taxonomiaSelecionada.getTipo().getNome().equals("Causa")) {
                List<EventoCausa> eventoCausas = eventoCausaRepository.findByCausaId(taxonomiaSelecionada.getCausa().getId());
                for (EventoCausa eventoCausa : eventoCausas) {
                    eventoCausa.setCausa(taxonomia.getCausa());
                }
                eventoCausaRepository.save(eventoCausas);
            }
            if(taxonomiaSelecionada.getTipo().getNome().equals("Consequência")) {
                List<EventoConsequencia> eventoConsequencias = eventoConsequenciaRepository.findByConsequenciaId(taxonomiaSelecionada.getConsequencia().getId());
                for (EventoConsequencia eventoConsequencia : eventoConsequencias) {
                    eventoConsequencia.setConsequencia(taxonomia.getConsequencia());
                }
                eventoConsequenciaRepository.save(eventoConsequencias);
            }
            if(taxonomiaSelecionada.getTipo().getNome().equals("Controle")) {
                List<ControleEvento> controleEventos = controleEventoRepository.findByControleId(taxonomiaSelecionada.getControle().getId());
                for (ControleEvento controleEvento : controleEventos) {
                    controleEvento.setControle(taxonomia.getControle());
                }
                controleEventoRepository.save(controleEventos);

                List<PlanoControle> planoControles = planoControleRepository.findByControleId(taxonomiaSelecionada.getControle().getId());
                for (PlanoControle planoControle : planoControles) {
                    planoControle.setControle(taxonomia.getControle());
                }
                planoControleRepository.save(planoControles);
            }

            taxonomiaRepository.save(taxonomiaSelecionada);
        }

        agrupamentoTaxonomia.setTaxonomias(taxonomias);
        agrupamentoTaxonomia = agrupamentoTaxonomiaRepository.save(agrupamentoTaxonomia);

        return agrupamentoTaxonomiaMapper.agrupamentoTaxonomiaToAgrupamentoTaxonomiaDTO(agrupamentoTaxonomia);
    }

    /**
     * Get all the taxonomias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgrupamentoTaxonomiaDTO> findAll(Pageable pageable, String descricao, Long inicio, Long fim, Long tipoTaxonomiaId) {
        log.debug("Request to get all Agrupamento Taxonomias");
        Page<AgrupamentoTaxonomia> result = agrupamentoTaxonomiaCustomRepositorio.listarAgrupamentoTaxonomias(pageable, descricao, inicio, fim, tipoTaxonomiaId);
        return result.map(agrupamentoTaxonomiaMapper::agrupamentoTaxonomiaToAgrupamentoTaxonomiaDTO);
    }

    @Override
    public List<TipoTaxonomiaDTO> findAllTiposTaxonomia() {
        List<TipoTaxonomia> result = tipoTaxonomiaRepository.findAll();
        return tipoTaxonomiaMapper.tipoTaxonomiasToTipoTaxonomiaDTOs(result);
    }

    @Override
    public List<TaxonomiaDTO> getTaxonomiaBySearch(String descricao, Long tipoId) {
        List<Taxonomia> result = taxonomiaCustomRepositorio.findBySearch(descricao, tipoId);
        return taxonomiaMapper.taxonomiasToTaxonomiaDTOs(result);
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaCustomRepositorio.searchByDescricao(descricao, false);
    }

    @Override
    public List<EventoDTO> getEventosBySearch(String descricao) {
        List<Evento> result = eventoCustomRepositorio.findBySearchAndOrgaoId(descricao, null);
        return eventoMapper.eventosToEventoDTOs(result);
    }

    @Override
    public List<CausaDTO> getCausasBySearch(String descricao) {
        List<Causa> result = causaCustomRepositorio.findBySearchAndOrgaoId(descricao, null);
        return causaMapper.causasToCausaDTOs(result);
    }

    @Override
    public List<ConsequenciaDTO> getConsequenciasBySearch(String descricao) {
        List<Consequencia> result = consequenciaCustomRepositorio.findBySearchAndOrgaoId(descricao, null);
        return consequenciaMapper.consequenciasToConsequenciaDTOs(result);
    }

    @Override
    public List<ControleDTO> getControlesBySearch(String descricao) {
        List<Controle> result = controleCustomRepositorio.findBySearchAndOrgaoId(descricao, null);
        return controleMapper.controlesToControleDTOs(result);
    }

    private Evento saveEvento(String descricao) {
        Evento evento = new Evento();
        evento.setOrgao(null);
        evento.setStatus(true);
        evento.setDescricao(descricao);
        evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
        evento = eventoRepository.save(evento);

        return evento;
    }

    private Causa saveCausa(String descricao) {
        Causa causa = new Causa();
        causa.setOrgao(null);
        causa.setStatus(true);
        causa.setDescricao(descricao);
        causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
        causa = causaRepository.save(causa);

        return causa;
    }

    private Consequencia saveConsequencia(String descricao) {
        Consequencia consequencia = new Consequencia();
        consequencia.setOrgao(null);
        consequencia.setStatus(true);
        consequencia.setDescricao(descricao);
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia = consequenciaRepository.save(consequencia);

        return consequencia;
    }

    private Controle saveControle(String descricao) {
        Controle consequencia = new Controle();
        consequencia.setOrgao(null);
        consequencia.setStatus(true);
        consequencia.setDescricao(descricao);
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia = controleRepository.save(consequencia);

        return consequencia;
    }
}