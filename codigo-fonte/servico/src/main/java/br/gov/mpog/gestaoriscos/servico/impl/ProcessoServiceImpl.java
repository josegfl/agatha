package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.ArquivoAnexo;
import br.gov.mpog.gestaoriscos.modelo.Avaliacao;
import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import br.gov.mpog.gestaoriscos.modelo.CalculoRisco;
import br.gov.mpog.gestaoriscos.modelo.Categoria;
import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.DecisaoProcesso;
import br.gov.mpog.gestaoriscos.modelo.Desenho;
import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.modelo.EventoCausa;
import br.gov.mpog.gestaoriscos.modelo.EventoConsequencia;
import br.gov.mpog.gestaoriscos.modelo.EventoRisco;
import br.gov.mpog.gestaoriscos.modelo.Identificacao;
import br.gov.mpog.gestaoriscos.modelo.ImpactoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.Macroprocesso;
import br.gov.mpog.gestaoriscos.modelo.MatrizSwot;
import br.gov.mpog.gestaoriscos.modelo.Natureza;
import br.gov.mpog.gestaoriscos.modelo.ObjetivoControle;
import br.gov.mpog.gestaoriscos.modelo.Operacao;
import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.Perfil;
import br.gov.mpog.gestaoriscos.modelo.PesoEventoRisco;
import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.ProbabilidadeCalculadora;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.ProcessoAnexo;
import br.gov.mpog.gestaoriscos.modelo.Responsavel;
import br.gov.mpog.gestaoriscos.modelo.RespostaRisco;
import br.gov.mpog.gestaoriscos.modelo.StatusProcesso;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.TipoControle;
import br.gov.mpog.gestaoriscos.modelo.TipoMatriz;
import br.gov.mpog.gestaoriscos.modelo.Usuario;
import br.gov.mpog.gestaoriscos.modelo.dto.ArquivoAnexoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleEventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.DecisaoProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.DesenhoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoCausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoRiscoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.IdentificacaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ObjetivoControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OperacaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaosDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PlanoControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoEtapaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ProcessoListagemDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.RespostaRiscoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.StatusProcessoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoMatrizDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.repositorio.ArquivoAnexoRepository;
import br.gov.mpog.gestaoriscos.repositorio.AvaliacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.CalculadoraRepository;
import br.gov.mpog.gestaoriscos.repositorio.CalculoRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.CategoriaRepository;
import br.gov.mpog.gestaoriscos.repositorio.CausaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.CausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ControleEventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.DecisaoProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.DesenhoRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoCausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.EventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.EventoRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.IdentificacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ImpactoCalculadoraRepository;
import br.gov.mpog.gestaoriscos.repositorio.MacroprocessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.NaturezaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ObjetivoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.OperacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PerfilRepository;
import br.gov.mpog.gestaoriscos.repositorio.PermissaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.PlanoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.RespostaRiscoRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.StatusTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoMatrizRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoTaxonomiaRepository;
import br.gov.mpog.gestaoriscos.repositorio.UsuarioRepository;
import br.gov.mpog.gestaoriscos.servico.ProcessoService;
import br.gov.mpog.gestaoriscos.servico.mapper.ArquivoAnexoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CalculadoraMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CategoriaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.CausaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ConsequenciaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ControleEventoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.DecisaoProcessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.DesenhoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoCausaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoConsequenciaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoRiscoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.MacroprocessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.NaturezaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ObjetivoControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.OperacaoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.OrgaoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.PerfilMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.PlanoControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.ProcessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.RespostaRiscoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.StatusProcessoMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TaxonomiaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoControleMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoMatrizMapper;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing Processo.
 */
@Service
@Transactional
public class ProcessoServiceImpl implements ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoServiceImpl.class);

    private static String GESTOR_DO_PROCESSO = "Gestor do Processo";

    private static String ANALISTA_DE_RISCO = "Analista de Risco";

    private static String UNIDADE = "Unidade";

    private static String NUCLEO = "Núcleo";

    private static String NAO_AVALIADO = "Não avaliado";

    private final ProcessoRepository processoRepository;

    private final StatusProcessoRepository statusProcessoRepository;

    private final UsuarioRepository usuarioRepository;

    private final TipoMatrizRepository tipoMatrizRepository;

    private final PerfilRepository perfilRepository;

    private final PermissaoRepository permissaoRepository;

    private final EventoRiscoRepository eventoRiscoRepository;

    private final IdentificacaoRepository identificacaoRepository;

    private final OrgaoRepository orgaoRepository;

    private final EventoRepository eventoRepository;

    private final EventoCustomRepositorio eventoCustomRepositorio;

    private final CausaRepository causaRepository;

    private final CausaCustomRepositorio causaCustomRepositorio;

    private final ConsequenciaRepository consequenciaRepository;

    private final ConsequenciaCustomRepositorio consequenciaCustomRepositorio;

    private final CategoriaRepository categoriaRepository;

    private final NaturezaRepository naturezaRepository;

    private final EventoCausaRepository eventoCausaRepository;

    private final EventoConsequenciaRepository eventoConsequenciaRepository;

    private final OrgaoCustomRepositorio orgaoCustomRepositorio;

    private final ProcessoCustomRepositorio processoCustomRepositorio;

    private final ArquivoAnexoRepository arquivoAnexoRepository;

    private final CalculadoraRepository calculadoraRepository;

    private final ControleCustomRepositorio controleCustomRepositorio;

    private final ControleRepository controleRepository;

    private final DesenhoRepository desenhoRepository;

    private final OperacaoRepository operacaoRepository;

    private final AvaliacaoRepository avaliacaoRepository;

    private final ControleEventoRepository controleEventoRepository;

    private final CalculoRiscoRepository calculoRiscoRepository;

    private final TipoTaxonomiaRepository tipoTaxonomiaRepository;

    private final StatusTaxonomiaRepository statusTaxonomiaRepository;

    private final TaxonomiaRepository taxonomiaRepository;

    private final RespostaRiscoRepository respostaRiscoRepository;

    private final TipoControleRepository tipoControleRepository;

    private final ObjetivoControleRepository objetivoControleRepository;

    private final PlanoControleRepository planoControleRepository;

    private final ProcessoMapper processoMapper;

    private final TipoMatrizMapper tipoMatrizMapper;

    private final EventoRiscoMapper eventoRiscoMapper;

    private final EventoMapper eventoMapper;

    private final CausaMapper causaMapper;

    private final ConsequenciaMapper consequenciaMapper;

    private final CategoriaMapper categoriaMapper;

    private final NaturezaMapper naturezaMapper;

    private final PerfilMapper perfilMapper;

    private final EventoCausaMapper eventoCausaMapper;

    private final EventoConsequenciaMapper eventoConsequenciaMapper;

    private final OrgaoMapper orgaoMapper;

    private final StatusProcessoMapper statusProcessoMapper;

    private final ArquivoAnexoMapper arquivoAnexoMapper;

    private final ControleMapper controleMapper;

    private final DesenhoMapper desenhoMapper;

    private final OperacaoMapper operacaoMapper;

    private final ControleEventoMapper controleEventoMapper;

    private final CalculadoraMapper calculadoraMapper;

    private final TaxonomiaMapper taxonomiaMapper;

    private final RespostaRiscoMapper respostaRiscoMapper;

    private final TipoControleMapper tipoControleMapper;

    private final ObjetivoControleMapper objetivoControleMapper;

    private final PlanoControleMapper planoControleMapper;

    private final MacroprocessoRepository macroprocessoRepository;

    private final ImpactoCalculadoraRepository impactoCalculadoraRepository;

    private final MacroprocessoMapper macroprocessoMapper;

    private final DecisaoProcessoRepository decisaoProcessoRepository;

    private final DecisaoProcessoMapper decisaoProcessoMapper;

    @Value("${mail.remetente}")
    private String remetente;

    @Value("${mail.destinatario}")
    private String destinatario;

    @Autowired
    public ProcessoServiceImpl(ProcessoRepository processoRepository, StatusProcessoRepository statusProcessoRepository, UsuarioRepository usuarioRepository, TipoMatrizRepository tipoMatrizRepository, PerfilRepository perfilRepository, PermissaoRepository permissaoRepository, EventoRiscoRepository eventoRiscoRepository, IdentificacaoRepository identificacaoRepository, OrgaoRepository orgaoRepository, EventoRepository eventoRepository, EventoCustomRepositorio eventoCustomRepositorio, CausaRepository causaRepository, CausaCustomRepositorio causaCustomRepositorio, ConsequenciaRepository consequenciaRepository, ConsequenciaCustomRepositorio consequenciaCustomRepositorio, CategoriaRepository categoriaRepository, NaturezaRepository naturezaRepository, EventoCausaRepository eventoCausaRepository, EventoConsequenciaRepository eventoConsequenciaRepository, OrgaoCustomRepositorio orgaoCustomRepositorio, ProcessoCustomRepositorio processoCustomRepositorio, ArquivoAnexoRepository arquivoAnexoRepository, CalculadoraRepository calculadoraRepository, ControleCustomRepositorio controleCustomRepositorio, ControleRepository controleRepository, DesenhoRepository desenhoRepository, OperacaoRepository operacaoRepository, AvaliacaoRepository avaliacaoRepository, ControleEventoRepository controleEventoRepository, CalculoRiscoRepository calculoRiscoRepository, TipoTaxonomiaRepository tipoTaxonomiaRepository, StatusTaxonomiaRepository statusTaxonomiaRepository, TaxonomiaRepository taxonomiaRepository, RespostaRiscoRepository respostaRiscoRepository, TipoControleRepository tipoControleRepository, ObjetivoControleRepository objetivoControleRepository, PlanoControleRepository planoControleRepository, ProcessoMapper processoMapper, TipoMatrizMapper tipoMatrizMapper, EventoRiscoMapper eventoRiscoMapper, EventoMapper eventoMapper, CausaMapper causaMapper, ConsequenciaMapper consequenciaMapper, CategoriaMapper categoriaMapper, NaturezaMapper naturezaMapper, PerfilMapper perfilMapper, EventoCausaMapper eventoCausaMapper, EventoConsequenciaMapper eventoConsequenciaMapper, OrgaoMapper orgaoMapper, StatusProcessoMapper statusProcessoMapper, ArquivoAnexoMapper arquivoAnexoMapper, ControleMapper controleMapper, DesenhoMapper desenhoMapper, OperacaoMapper operacaoMapper, ControleEventoMapper controleEventoMapper, CalculadoraMapper calculadoraMapper, TaxonomiaMapper taxonomiaMapper, RespostaRiscoMapper respostaRiscoMapper, TipoControleMapper tipoControleMapper, ObjetivoControleMapper objetivoControleMapper, PlanoControleMapper planoControleMapper, MacroprocessoRepository macroprocessoRepository, MacroprocessoMapper macroprocessoMapper, ImpactoCalculadoraRepository impactoCalculadoraRepository, DecisaoProcessoRepository decisaoProcessoRepository, DecisaoProcessoMapper decisaoProcessoMapper) {
        this.processoRepository = processoRepository;
        this.statusProcessoRepository = statusProcessoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoMatrizRepository = tipoMatrizRepository;
        this.perfilRepository = perfilRepository;
        this.permissaoRepository = permissaoRepository;
        this.eventoRiscoRepository = eventoRiscoRepository;
        this.identificacaoRepository = identificacaoRepository;
        this.orgaoRepository = orgaoRepository;
        this.eventoRepository = eventoRepository;
        this.eventoCustomRepositorio = eventoCustomRepositorio;
        this.causaRepository = causaRepository;
        this.causaCustomRepositorio = causaCustomRepositorio;
        this.consequenciaRepository = consequenciaRepository;
        this.consequenciaCustomRepositorio = consequenciaCustomRepositorio;
        this.categoriaRepository = categoriaRepository;
        this.naturezaRepository = naturezaRepository;
        this.eventoCausaRepository = eventoCausaRepository;
        this.eventoConsequenciaRepository = eventoConsequenciaRepository;
        this.orgaoCustomRepositorio = orgaoCustomRepositorio;
        this.processoCustomRepositorio = processoCustomRepositorio;
        this.arquivoAnexoRepository = arquivoAnexoRepository;
        this.calculadoraRepository = calculadoraRepository;
        this.controleCustomRepositorio = controleCustomRepositorio;
        this.controleRepository = controleRepository;
        this.desenhoRepository = desenhoRepository;
        this.operacaoRepository = operacaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.controleEventoRepository = controleEventoRepository;
        this.calculoRiscoRepository = calculoRiscoRepository;
        this.tipoTaxonomiaRepository = tipoTaxonomiaRepository;
        this.statusTaxonomiaRepository = statusTaxonomiaRepository;
        this.taxonomiaRepository = taxonomiaRepository;
        this.respostaRiscoRepository = respostaRiscoRepository;
        this.tipoControleRepository = tipoControleRepository;
        this.objetivoControleRepository = objetivoControleRepository;
        this.planoControleRepository = planoControleRepository;
        this.processoMapper = processoMapper;
        this.tipoMatrizMapper = tipoMatrizMapper;
        this.eventoRiscoMapper = eventoRiscoMapper;
        this.eventoMapper = eventoMapper;
        this.causaMapper = causaMapper;
        this.consequenciaMapper = consequenciaMapper;
        this.categoriaMapper = categoriaMapper;
        this.naturezaMapper = naturezaMapper;
        this.perfilMapper = perfilMapper;
        this.eventoCausaMapper = eventoCausaMapper;
        this.eventoConsequenciaMapper = eventoConsequenciaMapper;
        this.orgaoMapper = orgaoMapper;
        this.statusProcessoMapper = statusProcessoMapper;
        this.arquivoAnexoMapper = arquivoAnexoMapper;
        this.controleMapper = controleMapper;
        this.desenhoMapper = desenhoMapper;
        this.operacaoMapper = operacaoMapper;
        this.controleEventoMapper = controleEventoMapper;
        this.calculadoraMapper = calculadoraMapper;
        this.taxonomiaMapper = taxonomiaMapper;
        this.respostaRiscoMapper = respostaRiscoMapper;
        this.tipoControleMapper = tipoControleMapper;
        this.objetivoControleMapper = objetivoControleMapper;
        this.planoControleMapper = planoControleMapper;
        this.macroprocessoRepository = macroprocessoRepository;
        this.macroprocessoMapper = macroprocessoMapper;
        this.impactoCalculadoraRepository = impactoCalculadoraRepository;
        this.decisaoProcessoRepository = decisaoProcessoRepository;
        this.decisaoProcessoMapper = decisaoProcessoMapper;
    }

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.processoDTOToProcesso(processoDTO);
        String search = "";

        if (processo.getMacroprocesso() != null && processo.getMacroprocesso().getDescricao() != null) {
            processo.setMacroprocesso(saveMacroprocesso(processo.getMacroprocesso().getDescricao()));
            search += StringUtil.removerAcento(processo.getMacroprocesso().getDescricao()) + " ";
        }
        if (processo.getProcesso() != null) {
            search += StringUtil.removerAcento(processo.getProcesso());
        }
        processo.setSearch(search);

        if (processo.getId() == null) {
            StatusProcesso statusNaoFinalizado = statusProcessoRepository.findByNomeIgnoreCase("não finalizado");
            processo.setStatus(statusNaoFinalizado);
            processo.setDtCadastro(Calendar.getInstance());
        }

        processo.getAnalise().setProcesso(processo);
        for (MatrizSwot matrizSwot : processo.getAnalise().getMatrizes()) {
            matrizSwot.setAnalise(processo.getAnalise());
        }

        for (Responsavel responsavel : processo.getResponsaveis()) {
            responsavel.setProcesso(processo);
            responsavel.setUsuario(new Usuario(responsavel.getUsuario().getId()));
        }

        if (processo.getIdentificacao() == null || processo.getIdentificacao().getId() == null) {
            processo.setIdentificacao(new Identificacao(processo));
        } else {
            processo.getIdentificacao().setProcesso(processo);
        }

        if (processo.getAvaliacao() == null || processo.getAvaliacao().getId() == null) {
            processo.setAvaliacao(new Avaliacao(processo));
        } else {
            processo.getAvaliacao().setProcesso(processo);
        }

        if (processo.getCalculadoras() == null || processo.getCalculadoras().isEmpty()) {
            Calculadora inerente = calculadoraRepository.findByTipoNomeIgnoreCaseAndProcessoIsNull("Calculadora do Risco Inerente");
            Calculadora residual = calculadoraRepository.findByTipoNomeIgnoreCaseAndProcessoIsNull("Calculadora do Risco Residual");

            inerente = new Calculadora(inerente, processo);
            residual = new Calculadora(residual, processo);

            List<Calculadora> calculadoras = new ArrayList<>(0);
            calculadoras.add(inerente);
            calculadoras.add(residual);
            processo.setCalculadoras(calculadoras);
        } else {
            for (Calculadora calculadora : processo.getCalculadoras()) {
                calculadora.setProcesso(processo);
                for (ImpactoCalculadora impactoCalculadora : calculadora.getImpactos()) {
                    impactoCalculadora.setCalculadora(calculadora);
                }
                for (ProbabilidadeCalculadora probabilidadeCalculadora : calculadora.getProbabilidades()) {
                    probabilidadeCalculadora.setCalculadora(calculadora);
                }
            }
        }

        if (processo.getAnexos() != null) {
            for (ProcessoAnexo anexo : processo.getAnexos()) {
                anexo.setProcesso(processo);
            }
        }

        processo = processoRepository.save(processo);
        return processoMapper.processoToProcessoDTO(processo);
    }

    /**
     * Get all the processos.
     *
     * @param pageable  the pagination information
     * @param orgao     - Orgão da analise
     * @param descricao - Descrição do Macroprocesso ou processo
     * @param statusId  - Identificador do status do processo
     * @param inicio    - Data de Inicio do processo
     * @param fim       - Data de fim do processo
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProcessoListagemDTO> findAll(Pageable pageable, String cpf, String orgao, String descricao, Long statusId, Long inicio, Long fim) {
        log.debug("Request to get all Processos");

        Perfil analista = null;
        Perfil gestor = null;
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, NUCLEO);
        Perfil subcomite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Subcomitê");
        Perfil comite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Comitê");

        if (unidade == null && nucleo == null && subcomite == null && comite == null) {
            gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, GESTOR_DO_PROCESSO);
            analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, ANALISTA_DE_RISCO);
        }

        return processoCustomRepositorio.findByFilters(pageable, cpf, orgao, descricao, statusId, inicio, fim, gestor != null, analista != null);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProcessoDTO findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        Processo processo = processoRepository.findOne(id);
        return processoMapper.processoToProcessoDTO(processo);
    }

    /**
     * Delete the  processo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.delete(id);
    }

    @Override
    public List<UsuarioDTO> findAllAnalistas() {
        Perfil perfil = perfilRepository.findByNomeIgnoreCase(ANALISTA_DE_RISCO);
        List<Usuario> usuarios = usuarioRepository.findByPerfil(perfil.getId());
        List<UsuarioDTO> result = new ArrayList<>(0);
        usuarios.forEach(usuario -> result.add(new UsuarioDTO(usuario)));
        return result;
    }

    @Override
    public List<UsuarioDTO> findAllGestores() {
        Perfil perfil = perfilRepository.findByNomeIgnoreCase(GESTOR_DO_PROCESSO);
        List<Usuario> usuarios = usuarioRepository.findByPerfil(perfil.getId());
        List<UsuarioDTO> result = new ArrayList<>(0);
        usuarios.forEach(usuario -> result.add(new UsuarioDTO(usuario)));
        return result;
    }

    @Override
    public List<TipoMatrizDTO> findAllTiposMatriz() {
        List<TipoMatriz> result = tipoMatrizRepository.findAll();
        return tipoMatrizMapper.tipoMatrizsToTipoMatrizDTOs(result);
    }

    @Override
    public OrgaosDTO findOrgaosByCPF(String cpf) {
        Orgao secretaria = orgaoRepository.findByUsuarioCPF(cpf);
        Orgao orgao = secretaria.getOrgaoPai();

        if (orgao == null) {
            orgao = secretaria;
        }

        OrgaosDTO dto = new OrgaosDTO();
        dto.setOrgao(orgaoMapper.orgaoToOrgaoDTO(orgao));
        dto.setSecretaria(orgaoMapper.orgaoToOrgaoDTO(secretaria));
        return dto;
    }

    @Override
    public PermissaoProcessoDTO findPermissaoByCPF(String cpf) {
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, ANALISTA_DE_RISCO);
        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, GESTOR_DO_PROCESSO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, NUCLEO);
        Perfil subcomite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Subcomitê");
        Perfil comite = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(cpf, "Comitê");

        PermissaoProcessoDTO permissaoProcessoDTO = new PermissaoProcessoDTO();
        permissaoProcessoDTO.setCriar(gestor != null || analista != null || unidade != null);
        permissaoProcessoDTO.setValidar(gestor != null);
        permissaoProcessoDTO.setExcluir(unidade != null);
        permissaoProcessoDTO.setEnviarParaValidacao(analista != null);
        permissaoProcessoDTO.setCriarEventoRisco(gestor != null || analista != null);

        if (nucleo != null || subcomite != null || comite != null) {
            permissaoProcessoDTO.setConsultarUnidade(true);
        } else {
            Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
            permissaoProcessoDTO.setOrgao(orgaoMapper.orgaoToOrgaoDTO(orgao));
        }

        return permissaoProcessoDTO;
    }

    /**
     * Get all the eventos de risco.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoRiscoDTO> findAllEventosRisco(Pageable pageable, Long processoId) {
        log.debug("Request to get all eventos de risco");

        Page<EventoRisco> result = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId, pageable);
        return result.map(eventoRiscoMapper::eventoRiscoToEventoRiscoDTO);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventoRiscoDTO findEventoRisco(Long id) {
        log.debug("Request to get Processo : {}", id);
        EventoRisco eventoRisco = eventoRiscoRepository.findOne(id);
        return eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
    }

    @Override
    public EventoRiscoDTO saveEventoRisco(EventoRiscoDTO eventoRiscoDTO, String cpf) {
        EventoRisco eventoRisco = eventoRiscoMapper.eventoRiscoDTOToEventoRisco(eventoRiscoDTO);
        eventoRisco.setIdentificacao(identificacaoRepository.findByProcessoId(eventoRiscoDTO.getIdentificacao().getProcesso().getId()));

        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        if (eventoRisco.getEvento().getId() == null) {
            Evento evento = eventoRisco.getEvento();
            evento.setOrgao(orgao);
            evento.setStatus(true);
            evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
            evento = eventoRepository.save(evento);

            eventoRisco.setEvento(evento);
        }

        for (EventoCausa eventoCausa : eventoRisco.getCausas()) {
            eventoCausa = verificaEventoCausa(eventoCausa, cpf);
            eventoCausa.setEventoRisco(eventoRisco);
        }

        for (EventoConsequencia eventoConsequencia : eventoRisco.getConsequencias()) {
            eventoConsequencia = verificaEventoConsequencia(eventoConsequencia, cpf);
            eventoConsequencia.setEventoRisco(eventoRisco);
        }

        if (eventoRisco.getControleEventos() != null && !eventoRisco.getControleEventos().isEmpty()) {
            for (ControleEvento controleEvento : eventoRisco.getControleEventos()) {
                controleEvento.setEventoRisco(eventoRisco);
                if (controleEvento.getControle().getId() == null) {
                    Controle controle = controleEvento.getControle();
                    controle.setOrgao(orgao);
                    controle.setStatus(true);
                    controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
                    controle = controleRepository.save(controle);

                    controleEvento.setControle(controle);
                }
            }
        }

        if (eventoRisco.getControles() != null) {
            for (PlanoControle planoControle : eventoRisco.getControles()) {
                planoControle.setEventoRisco(eventoRisco);
                if (planoControle.getControle().getId() == null) {
                    Controle controle = planoControle.getControle();
                    controle.setOrgao(orgao);
                    controle.setStatus(true);
                    controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
                    controle = controleRepository.save(controle);

                    planoControle.setControle(controle);
                }
            }
        }

        eventoRisco = eventoRiscoRepository.save(eventoRisco);
        verificarCalculoRisco(eventoRisco);
        return eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
    }

    private EventoCausa verificaEventoCausa(EventoCausa eventoCausa, String cpf) {
        if (eventoCausa.getCausa().getId() == null) {
            Causa causa = saveCausa(eventoCausa.getCausa().getDescricao(), cpf);
            eventoCausa.setCausa(causa);
        } else {
            Causa causaPersistido = causaRepository.findOne(eventoCausa.getCausa().getId());
            if (!eventoCausa.getCausa().getDescricao().equalsIgnoreCase(causaPersistido.getDescricao())) {
                Causa causa = saveCausa(eventoCausa.getCausa().getDescricao(), cpf);
                eventoCausa.setCausa(causa);
            }
        }

        return eventoCausa;
    }

    private EventoConsequencia verificaEventoConsequencia(EventoConsequencia eventoConsequencia, String cpf) {
        if (eventoConsequencia.getConsequencia().getId() == null) {
            Consequencia consequencia = saveConsequencia(eventoConsequencia.getConsequencia().getDescricao(), cpf);
            eventoConsequencia.setConsequencia(consequencia);
        } else {
            Consequencia consequenciaPersistido = consequenciaRepository.findOne(eventoConsequencia.getConsequencia().getId());
            if (!eventoConsequencia.getConsequencia().getDescricao().equalsIgnoreCase(consequenciaPersistido.getDescricao())) {
                Consequencia consequencia = saveConsequencia(eventoConsequencia.getConsequencia().getDescricao(), cpf);
                eventoConsequencia.setConsequencia(consequencia);
            }
        }

        return eventoConsequencia;
    }

    @Override
    public void deleteEventoRisco(Long id) {
        log.debug("Request to delete Evento Risco : {}", id);
        eventoRiscoRepository.delete(id);
    }

    @Override
    public List<EventoDTO> findEventoByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Evento> result = eventoCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return eventoMapper.eventosToEventoDTOs(result);
    }

    @Override
    public List<CausaDTO> findCausaByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Causa> result = causaCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return causaMapper.causasToCausaDTOs(result);
    }

    @Override
    public List<ConsequenciaDTO> findConsequenciaByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Consequencia> result = consequenciaCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return consequenciaMapper.consequenciasToConsequenciaDTOs(result);
    }

    @Override
    public List<CategoriaDTO> findAllCategoria() {
        List<Categoria> result = categoriaRepository.findByStatusTrueOrderByDescricaoAsc();
        return categoriaMapper.categoriasToCategoriaDTOs(result);
    }

    @Override
    public List<NaturezaDTO> findAllNatureza() {
        List<Natureza> result = naturezaRepository.findByStatusTrueOrderByDescricaoAsc();
        return naturezaMapper.naturezasToNaturezaDTOs(result);
    }

    @Override
    public PerfilDTO verificarEvento(EventoRiscoDTO eventoRiscoDTO) {
        EventoRisco eventoRisco = eventoRiscoMapper.eventoRiscoDTOToEventoRisco(eventoRiscoDTO);
        EventoRisco eventoRiscoPersisted = eventoRiscoRepository.findOne(eventoRiscoDTO.getId());
        Evento evento = eventoRepository.findOne(eventoRiscoDTO.getEvento().getId());

        if (!eventoRiscoDTO.getEvento().getId().equals(eventoRiscoPersisted.getEvento().getId())) {
            Evento novoEvento = new Evento();
            novoEvento.setId(eventoRiscoDTO.getEvento().getId());
            eventoRiscoPersisted.setEvento(novoEvento);

            eventoRiscoRepository.save(eventoRiscoPersisted);
            return null;
        }
        if (eventoRiscoDTO.getEvento().getDescricao().equals(eventoRiscoPersisted.getEvento().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoRiscoDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            if (evento.getOrgao() == null) {
                return perfilMapper.perfilToPerfilDTO(nucleo);
            }
        } else if (unidade != null) {
            if (evento.getOrgao() == null) {
                saveEventoInEventoRisco(eventoRisco, eventoRiscoDTO.getCpf());
                return null;
            } else {
                if (eventoHasProcesso(eventoRisco, evento)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (evento.getOrgao() == null) {
                saveEventoInEventoRisco(eventoRisco, eventoRiscoDTO.getCpf());
                return null;
            } else {
                if (eventoHasProcesso(eventoRisco, evento)) {
                    return null;
                } else {
                    saveEventoInEventoRisco(eventoRisco, eventoRiscoDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public EventoDTO atualizarEvento(EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findOne(eventoDTO.getId());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
        evento = eventoRepository.save(evento);
        return eventoMapper.eventoToEventoDTO(evento);
    }

    @Override
    public EventoRiscoDTO substituirEvento(EventoRiscoDTO eventoRiscoDTO) {
        EventoRisco eventoRisco = eventoRiscoRepository.findOne(eventoRiscoDTO.getId());

        Evento evento = saveEvento(eventoRiscoDTO.getEvento().getDescricao(), eventoRiscoDTO.getCpf());

        eventoRisco.setEvento(evento);
        eventoRisco = eventoRiscoRepository.save(eventoRisco);

        return eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
    }

    @Override
    public PerfilDTO verificarCausa(EventoCausaDTO eventoCausaDTO) {
        EventoCausa eventoCausa = eventoCausaMapper.eventoCausaDTOToEventoCausa(eventoCausaDTO);
        EventoCausa eventoCausaPersisted = eventoCausaRepository.findOne(eventoCausaDTO.getId());
        Causa causa = causaRepository.findOne(eventoCausaDTO.getCausa().getId());

        if (!eventoCausaDTO.getCausa().getId().equals(eventoCausaPersisted.getCausa().getId())) {
            Causa novaCausa = new Causa();
            novaCausa.setId(eventoCausaDTO.getCausa().getId());
            eventoCausaPersisted.setCausa(novaCausa);

            eventoCausaRepository.save(eventoCausaPersisted);
            return null;
        }
        if (eventoCausaDTO.getCausa().getDescricao().equals(eventoCausaPersisted.getCausa().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoCausaDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            if (causa.getOrgao() == null) {
                return perfilMapper.perfilToPerfilDTO(nucleo);
            }
        } else if (unidade != null) {
            if (causa.getOrgao() == null) {
                saveCausaInEventoCausa(eventoCausa, eventoCausaDTO.getCpf());
                return null;
            } else {
                if (causaHasProcesso(eventoCausa, causa)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (causa.getOrgao() == null) {
                saveCausaInEventoCausa(eventoCausa, eventoCausaDTO.getCpf());
                return null;
            } else {
                if (causaHasProcesso(eventoCausa, causa)) {
                    return null;
                } else {
                    saveCausaInEventoCausa(eventoCausa, eventoCausaDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public CausaDTO atualizarCausa(CausaDTO causaDTO) {
        Causa causa = causaRepository.findOne(causaDTO.getId());
        causa.setDescricao(causaDTO.getDescricao());
        causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
        causa.setStatus(true);
        causa = causaRepository.save(causa);
        return causaMapper.causaToCausaDTO(causa);
    }

    @Override
    public EventoCausaDTO substituirCausa(EventoCausaDTO eventoCausaDTO) {
        EventoCausa eventoCausa = eventoCausaRepository.findOne(eventoCausaDTO.getId());

        Causa causa = saveCausa(eventoCausaDTO.getCausa().getDescricao(), eventoCausaDTO.getCpf());

        eventoCausa.setCausa(causa);
        eventoCausa = eventoCausaRepository.save(eventoCausa);

        return eventoCausaMapper.eventoCausaToEventoCausaDTO(eventoCausa);
    }

    @Override
    public PerfilDTO verificarConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO) {
        EventoConsequencia eventoConsequencia = eventoConsequenciaMapper.eventoConsequenciaDTOToEventoConsequencia(eventoConsequenciaDTO);
        EventoConsequencia eventoConsequenciaPersisted = eventoConsequenciaRepository.findOne(eventoConsequenciaDTO.getId());
        Consequencia consequencia = consequenciaRepository.findOne(eventoConsequenciaDTO.getConsequencia().getId());

        if (!eventoConsequenciaDTO.getConsequencia().getId().equals(eventoConsequenciaPersisted.getConsequencia().getId())) {
            Consequencia novaConsequencia = new Consequencia();
            novaConsequencia.setId(eventoConsequenciaDTO.getConsequencia().getId());
            eventoConsequenciaPersisted.setConsequencia(novaConsequencia);

            eventoConsequenciaRepository.save(eventoConsequenciaPersisted);
            return null;
        }
        if (eventoConsequenciaDTO.getConsequencia().getDescricao().equals(eventoConsequenciaPersisted.getConsequencia().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(eventoConsequenciaDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            if (consequencia.getOrgao() == null) {
                return perfilMapper.perfilToPerfilDTO(nucleo);
            }
        } else if (unidade != null) {
            if (consequencia.getOrgao() == null) {
                saveConsequenciaInEventoConsequencia(eventoConsequencia, eventoConsequenciaDTO.getCpf());
                return null;
            } else {
                if (consequenciaHasProcesso(eventoConsequencia, consequencia)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (consequencia.getOrgao() == null) {
                saveConsequenciaInEventoConsequencia(eventoConsequencia, eventoConsequenciaDTO.getCpf());
                return null;
            } else {
                if (consequenciaHasProcesso(eventoConsequencia, consequencia)) {
                    return null;
                } else {
                    saveConsequenciaInEventoConsequencia(eventoConsequencia, eventoConsequenciaDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public ConsequenciaDTO atualizarConsequencia(ConsequenciaDTO consequenciaDTO) {
        Consequencia consequencia = consequenciaRepository.findOne(consequenciaDTO.getId());
        consequencia.setDescricao(consequenciaDTO.getDescricao());
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia.setStatus(true);
        consequencia = consequenciaRepository.save(consequencia);
        return consequenciaMapper.consequenciaToConsequenciaDTO(consequencia);
    }

    @Override
    public EventoConsequenciaDTO substituirConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO) {
        EventoConsequencia eventoConsequencia = eventoConsequenciaRepository.findOne(eventoConsequenciaDTO.getId());

        Consequencia consequencia = saveConsequencia(eventoConsequenciaDTO.getConsequencia().getDescricao(), eventoConsequenciaDTO.getCpf());

        eventoConsequencia.setConsequencia(consequencia);
        eventoConsequencia = eventoConsequenciaRepository.save(eventoConsequencia);

        return eventoConsequenciaMapper.eventoConsequenciaToEventoConsequenciaDTO(eventoConsequencia);
    }

    @Override
    public List<String> searchOrgaoByNome(String nome) {
        return orgaoCustomRepositorio.searchByNomeAndOrgaoMP(nome);
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return processoCustomRepositorio.searchByDescricao(descricao);
    }

    @Override
    public List<StatusProcessoDTO> getStatus() {
        List<StatusProcesso> result = statusProcessoRepository.findAll();
        return statusProcessoMapper.statusProcessosToStatusProcessoDTOs(result);
    }

    @Override
    public List<ArquivoAnexoDTO> uploadFile(MultipartFile[] arquivos, String[] nomes) throws IOException {
        List<ArquivoAnexo> anexos = new ArrayList<>(0);
        int index = 0;

        for (MultipartFile arquivo : arquivos) {
            ArquivoAnexo arquivoAnexo = new ArquivoAnexo();
            arquivoAnexo.setArquivo(arquivo.getBytes());
            arquivoAnexo.setTamanho(arquivoAnexo.getArquivo().length);
            arquivoAnexo.setNomeArquivo(nomes[index]);
            arquivoAnexo.setNomeDocumento(nomes[index]);
            arquivoAnexo.setDataUpload(new Date());

            arquivoAnexo = arquivoAnexoRepository.save(arquivoAnexo);

            anexos.add(arquivoAnexo);
            index++;
        }

        return arquivoAnexoMapper.arquivoAnexosToArquivoAnexoDTOs(anexos);
    }

    @Override
    public ArquivoAnexo getAnexoById(Long id) {
        return arquivoAnexoRepository.findOne(id);
    }

    @Override
    public List<ControleDTO> findControleByDescricaoAndCPF(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);
        List<Controle> result = controleCustomRepositorio.findBySearchAndOrgaoId(descricao, orgao.getId());
        return controleMapper.controlesToControleDTOs(result);
    }

    @Override
    public List<DesenhoDTO> findAllDesenhos() {
        List<Desenho> result = desenhoRepository.findByStatusTrueOrderByDescricaoAsc();
        return desenhoMapper.desenhosToDesenhoDTOs(result);
    }

    @Override
    public List<OperacaoDTO> findAllOperacoes() {
        List<Operacao> result = operacaoRepository.findByStatusTrueOrderByDescricaoAsc();
        return operacaoMapper.operacaosToOperacaoDTOs(result);
    }

    @Override
    public PerfilDTO verificarControle(ControleEventoDTO controleEventoDTO) {
        ControleEvento controleEvento = controleEventoMapper.controleEventoDTOToControleEvento(controleEventoDTO);
        ControleEvento controleEventoPersisted = controleEventoRepository.findOne(controleEventoDTO.getId());
        Controle controle = controleRepository.findOne(controleEventoDTO.getControle().getId());

        atualizarControleEvento(controleEventoPersisted, controleEventoDTO);

        if (!controleEventoDTO.getControle().getId().equals(controleEventoPersisted.getControle().getId())) {
            Controle novaControle = new Controle();
            novaControle.setId(controleEventoDTO.getControle().getId());
            controleEventoPersisted.setControle(novaControle);

            controleEventoRepository.save(controleEventoPersisted);
            return null;
        }
        if (controleEventoDTO.getControle().getDescricao().equals(controleEventoPersisted.getControle().getDescricao())) {
            return null;
        }

        Perfil gestor = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), GESTOR_DO_PROCESSO);
        Perfil analista = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), ANALISTA_DE_RISCO);
        Perfil unidade = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), UNIDADE);
        Perfil nucleo = permissaoRepository.findPerfilByUsuarioIdAndNomeIgnoreCase(controleEventoDTO.getCpf(), NUCLEO);

        if (nucleo != null) {
            if (controle.getOrgao() == null) {
                return perfilMapper.perfilToPerfilDTO(nucleo);
            }
        } else if (unidade != null) {
            if (controle.getOrgao() == null) {
                saveControleInControleEvento(controleEvento, controleEventoDTO.getCpf());
                return null;
            } else {
                if (controleHasProcesso(controleEvento, controle)) {
                    return null;
                } else {
                    return perfilMapper.perfilToPerfilDTO(unidade);
                }
            }
        } else if (gestor != null || analista != null) {
            if (controle.getOrgao() == null) {
                saveControleInControleEvento(controleEvento, controleEventoDTO.getCpf());
                return null;
            } else {
                if (controleHasProcesso(controleEvento, controle)) {
                    return null;
                } else {
                    saveControleInControleEvento(controleEvento, controleEventoDTO.getCpf());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public ControleDTO atualizarControle(ControleDTO controleDTO) {
        Controle controle = controleRepository.findOne(controleDTO.getId());
        controle.setDescricao(controleDTO.getDescricao());
        controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
        controle.setStatus(true);
        controle = controleRepository.save(controle);
        return controleMapper.controleToControleDTO(controle);
    }

    @Override
    public ControleEventoDTO substituirControle(ControleEventoDTO controleEventoDTO) {
        ControleEvento controleEvento = controleEventoRepository.findOne(controleEventoDTO.getId());

        Controle controle = saveControle(controleEventoDTO.getControle().getDescricao(), controleEventoDTO.getCpf());

        controleEvento.setControle(controle);
        controleEvento = controleEventoRepository.save(controleEvento);

        return controleEventoMapper.controleEventoToControleEventoDTO(controleEvento);
    }

    @Override
    public CalculadoraDTO getCalculadoraInerenteByProcessoId(Long processoId) {
        Calculadora result = calculadoraRepository.findByTipoNomeIgnoreCaseAndProcessoId("Calculadora do Risco Inerente", processoId);
        return calculadoraMapper.calculadoraToCalculadoraDTO(result);
    }

    @Override
    public CalculadoraDTO getCalculadoraResidualByProcessoId(Long processoId) {
        Calculadora result = calculadoraRepository.findByTipoNomeIgnoreCaseAndProcessoId("Calculadora do Risco Residual", processoId);
        return calculadoraMapper.calculadoraToCalculadoraDTO(result);
    }

    @Override
    public List<EventoRiscoDTO> findAllEventosRisco(Long processoId) {
        List<EventoRisco> result = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
        return eventoRiscoMapper.eventoRiscosToEventoRiscoDTOs(result);
    }

    @Override
    public Void salvarCalculo(IdentificacaoDTO identificacaoDTO) {
        List<EventoRisco> eventos = eventoRiscoMapper.eventoRiscoDTOsToEventoRiscos(identificacaoDTO.getEventos());

        for (EventoRisco evento : eventos) {
            verificarCalculoRisco(evento);
        }

        return null;
    }

    @Override
    public Boolean getIgnorarRiscoInerenteByProcesso(Long processoId) {
        Avaliacao avaliacao = avaliacaoRepository.findByProcessoId(processoId);
        return avaliacao.getIgnorarRiscoInerente();
    }

    @Override
    public Boolean alterarIgnorarRiscoInerente(Long processoId) {
        Avaliacao avaliacao = avaliacaoRepository.findByProcessoId(processoId);
        avaliacao.setIgnorarRiscoInerente(!avaliacao.getIgnorarRiscoInerente());
        avaliacao = avaliacaoRepository.save(avaliacao);
        if (avaliacao.getIgnorarRiscoInerente()) {
            List<EventoRisco> eventos = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
            for (EventoRisco evento : eventos) {
                evento.setCalculoRiscoInerente(null);
            }
            eventoRiscoRepository.save(eventos);
        }
        return avaliacao.getIgnorarRiscoInerente();
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaEvento(EventoDTO eventoDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (eventoDTO.getId() == null) {
            taxonomia.setEvento(saveEvento(eventoDTO.getDescricao(), eventoDTO.getCpf()));
        } else {
            Evento eventoPersistido = eventoRepository.findOne(eventoDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(eventoDTO.getDescricao())) {
                taxonomia.setEvento(saveEvento(eventoDTO.getDescricao(), eventoDTO.getCpf()));
            } else {
                taxonomia.setEvento(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Evento"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getEvento().getDescricao());
        taxonomia.setSearch(taxonomia.getEvento().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(eventoDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaCausa(CausaDTO causaDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (causaDTO.getId() == null) {
            taxonomia.setCausa(saveCausa(causaDTO.getDescricao(), causaDTO.getCpf()));
        } else {
            Causa eventoPersistido = causaRepository.findOne(causaDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(causaDTO.getDescricao())) {
                taxonomia.setCausa(saveCausa(causaDTO.getDescricao(), causaDTO.getCpf()));
            } else {
                taxonomia.setCausa(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Causa"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getCausa().getDescricao());
        taxonomia.setSearch(taxonomia.getCausa().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(causaDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaConsequencia(ConsequenciaDTO consequenciaDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (consequenciaDTO.getId() == null) {
            taxonomia.setConsequencia(saveConsequencia(consequenciaDTO.getDescricao(), consequenciaDTO.getCpf()));
        } else {
            Consequencia eventoPersistido = consequenciaRepository.findOne(consequenciaDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(consequenciaDTO.getDescricao())) {
                taxonomia.setConsequencia(saveConsequencia(consequenciaDTO.getDescricao(), consequenciaDTO.getCpf()));
            } else {
                taxonomia.setConsequencia(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Consequência"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getConsequencia().getDescricao());
        taxonomia.setSearch(taxonomia.getConsequencia().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(consequenciaDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public TaxonomiaDTO salvarTaxonomiaControle(ControleDTO controleDTO) {
        Taxonomia taxonomia = new Taxonomia();

        if (controleDTO.getId() == null) {
            taxonomia.setControle(saveControle(controleDTO.getDescricao(), controleDTO.getCpf()));
        } else {
            Controle eventoPersistido = controleRepository.findOne(controleDTO.getId());

            if (!eventoPersistido.getDescricao().equalsIgnoreCase(controleDTO.getDescricao())) {
                taxonomia.setControle(saveControle(controleDTO.getDescricao(), controleDTO.getCpf()));
            } else {
                taxonomia.setControle(eventoPersistido);
            }
        }

        taxonomia.setTipo(tipoTaxonomiaRepository.findByNomeIgnoreCase("Controle"));
        taxonomia.setStatus(statusTaxonomiaRepository.findByNomeIgnoreCase(NAO_AVALIADO));

        taxonomia.setDescricao(taxonomia.getControle().getDescricao());
        taxonomia.setSearch(taxonomia.getControle().getSearch());
        taxonomia.setDtCadastro(Calendar.getInstance());
        taxonomia.setOrgao(orgaoRepository.findByUsuarioCPF(controleDTO.getCpf()));

        taxonomia = taxonomiaRepository.save(taxonomia);

        return taxonomiaMapper.taxonomiaToTaxonomiaDTO(taxonomia);
    }

    @Override
    public List<RespostaRiscoDTO> getTiposResposta() {
        List<RespostaRisco> result = respostaRiscoRepository.findAll();
        return respostaRiscoMapper.respostaRiscosToRespostaRiscoDTOs(result);
    }

    @Override
    public List<TipoControleDTO> getTiposControle() {
        List<TipoControle> result = tipoControleRepository.findAll();
        return tipoControleMapper.tipoControlesToTipoControleDTOs(result);
    }

    @Override
    public List<ObjetivoControleDTO> getObjetivosControle() {
        List<ObjetivoControle> result = objetivoControleRepository.findAll();
        return objetivoControleMapper.objetivoControlesToObjetivoControleDTOs(result);
    }

    @Override
    public List<MacroprocessoDTO> findAllMacroprocesso() {
        List<Macroprocesso> restul = macroprocessoRepository.findByStatusTrueOrderByDescricaoAsc();
        return macroprocessoMapper.macroprocessosToMacroprocessoDTOs(restul);
    }

    @Override
    public Boolean verificaRiscoInerente(Long processoId) {
        Boolean valido = getIgnorarRiscoInerenteByProcesso(processoId);
        if (valido) {
            return true;
        } else {
            List<EventoRisco> eventos = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
            for (EventoRisco evento : eventos) {
                if (evento.getCalculoRiscoInerente() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean verificaControleEvento(Long processoId) {
        List<EventoRisco> eventos = eventoRiscoRepository.findByIdentificacaoProcessoIdOrderByIdAsc(processoId);
        for (EventoRisco evento : eventos) {
            if (!evento.getControleEventos().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void alterarStatusImpactoCalculadora(CalculadoraDTO calculadoraDTO) {
        Calculadora calculadora = calculadoraRepository.findOne(calculadoraDTO.getId());
        for (int i = 0; i < calculadora.getImpactos().size(); i++) {
            atualizaImpacto(calculadora.getImpactos().get(i).getId(), calculadoraDTO.getImpactos().get(i).getDesabilitado());
        }
    }

    @Override
    public void alterarStatusImpactoCalculadoras(Long processoId, CalculadoraDTO calculadoraDTO) {
        List<Calculadora> calculadoras = calculadoraRepository.findByProcessoId(processoId);

        for (Calculadora calculadora : calculadoras) {
            for (int i = 0; i < calculadora.getImpactos().size(); i++) {
                atualizaImpacto(calculadora.getImpactos().get(i).getId(), calculadoraDTO.getImpactos().get(i).getDesabilitado());
            }
        }
    }

    @Override
    public void saveRespostaRisco(Long eventoRiscoId, EventoRiscoDTO eventoRiscoDTO) {
        RespostaRisco respostaRisco = respostaRiscoMapper.respostaRiscoDTOToRespostaRisco(eventoRiscoDTO.getRespostaRisco());
        eventoRiscoRepository.saveRespostaRisco(eventoRiscoId, respostaRisco, eventoRiscoDTO.getJustificativaRespostaRisco());
    }

    @Override
    public EventoRiscoDTO findEventoWithControles(Long eventoRiscoId) {
        EventoRisco eventoRisco = eventoRiscoRepository.findOne(eventoRiscoId);
        List<PlanoControle> controles = planoControleRepository.findByEventoRiscoId(eventoRiscoId);
        EventoRiscoDTO result = eventoRiscoMapper.eventoRiscoToEventoRiscoDTO(eventoRisco);
        result.setControles(planoControleMapper.planoControlesToPlanoControleDTOs(controles));
        return result;
    }

    @Override
    public PlanoControleDTO savePlanoControle(PlanoControleDTO planoControleDTO) {
        PlanoControle planoControle = planoControleMapper.planoControleDTOToPlanoControle(planoControleDTO);

        if (planoControle.getControle().getId() == null) {
            Controle controle = saveControle(planoControle.getControle().getDescricao(), planoControleDTO.getCpf());

            planoControle.setControle(controle);
        }

        planoControle = planoControleRepository.save(planoControle);
        return planoControleMapper.planoControleToPlanoControleDTO(planoControle);
    }

    @Override
    public void deletePlanoControle(Long planoControleId) {
        planoControleRepository.delete(planoControleId);
    }

    @Override
    public ProcessoEtapaDTO getProcessoEtapa(Long processoId) {
        ProcessoEtapaDTO processoEtapaDTO = new ProcessoEtapaDTO();
        processoEtapaDTO.setAvaliacao(eventoRiscoRepository.countByIdentificacaoProcessoId(processoId) > 0);
        processoEtapaDTO.setResposta(controleEventoRepository.countByEventoRiscoIdentificacaoProcessoId(processoId) > 0 && calculoRiscoRepository.countNiveisByProcessoId(processoId) > 0);
        processoEtapaDTO.setPlano(respostaRiscoRepository.countByProcessoId(processoId) > 0);
        processoEtapaDTO.setValidacao(planoControleRepository.countByEventoRiscoIdentificacaoProcessoId(processoId) > 0);
        return processoEtapaDTO;
    }

    @Override
    public List<DecisaoProcessoDTO> findAllDecisoes() {
        List<DecisaoProcesso> result = decisaoProcessoRepository.findByOrderByNomeAsc();
        return decisaoProcessoMapper.decisaoProcessosToDecisaoProcessoDTOs(result);
    }

    @Override
    public void validarProcesso(ProcessoDTO processoDTO) {
        Processo processo = processoRepository.findOne(processoDTO.getId());
        processo.setJustificativa(processoDTO.getJustificativa());
        processo.setDecisao(decisaoProcessoMapper.decisaoProcessoDTOToDecisaoProcesso(processoDTO.getDecisao()));

        if (processo.getDecisao().getId().equals(decisaoProcessoRepository.findByNomeIgnoreCase("validar").getId())) {
            processo.setStatus(statusProcessoRepository.findByNomeIgnoreCase("finalizado"));
        }else{
            processo.setStatus(statusProcessoRepository.findByNomeIgnoreCase("não finalizado"));
        }

        processoRepository.save(processo);
    }

    private void atualizaImpacto(Long impactoId, Boolean desabilitado) {
        ImpactoCalculadora impactoCalculadora = impactoCalculadoraRepository.findOne(impactoId);
        impactoCalculadora.setDesabilitado(desabilitado);
        impactoCalculadoraRepository.save(impactoCalculadora);
    }

    private Evento saveEvento(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Evento evento = new Evento();
        evento.setOrgao(orgao);
        evento.setStatus(true);
        evento.setDescricao(descricao);
        evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
        evento = eventoRepository.save(evento);

        return evento;
    }

    private Causa saveCausa(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Causa causa = new Causa();
        causa.setOrgao(orgao);
        causa.setStatus(true);
        causa.setDescricao(descricao);
        causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
        causa = causaRepository.save(causa);

        return causa;
    }

    private Consequencia saveConsequencia(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Consequencia consequencia = new Consequencia();
        consequencia.setOrgao(orgao);
        consequencia.setStatus(true);
        consequencia.setDescricao(descricao);
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia = consequenciaRepository.save(consequencia);

        return consequencia;
    }

    private Controle saveControle(String descricao, String cpf) {
        Orgao orgao = orgaoRepository.findByUsuarioCPF(cpf);

        Controle controle = new Controle();
        controle.setOrgao(orgao);
        controle.setStatus(true);
        controle.setDescricao(descricao);
        controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
        controle = controleRepository.save(controle);

        return controle;
    }

    private Macroprocesso saveMacroprocesso(String descricao) {
        Macroprocesso macroprocesso = macroprocessoRepository.findBySearchIgnoreCase(StringUtil.removerAcento(descricao));

        if (macroprocesso == null) {
            macroprocesso = new Macroprocesso();
            macroprocesso.setStatus(true);
            macroprocesso.setDescricao(descricao);
            macroprocesso.setSearch(StringUtil.removerAcento(macroprocesso.getDescricao()));
            macroprocesso = macroprocessoRepository.save(macroprocesso);
        }

        return macroprocesso;
    }

    private void verificarCalculoRisco(EventoRisco eventoRisco) {

        CalculoRisco calculoRiscoInerente = eventoRisco.getCalculoRiscoInerente();
        CalculoRisco calculoRiscoResidual = eventoRisco.getCalculoRiscoResidual();

        eventoRisco.setCalculoRiscoInerente(null);
        eventoRisco.setCalculoRiscoResidual(null);

        EventoRisco evento = eventoRiscoRepository.findOne(eventoRisco.getId());

        if (calculoRiscoInerente != null) {
            for (PesoEventoRisco pesoEventoRisco : calculoRiscoInerente.getPesos()) {
                pesoEventoRisco.setCalculoRisco(calculoRiscoInerente);
            }

            calculoRiscoInerente = calculoRiscoRepository.save(calculoRiscoInerente);

            evento.setCalculoRiscoInerente(calculoRiscoInerente);
        }

        if (calculoRiscoResidual != null) {
            for (PesoEventoRisco pesoEventoRisco : calculoRiscoResidual.getPesos()) {
                pesoEventoRisco.setCalculoRisco(calculoRiscoResidual);
            }

            calculoRiscoResidual = calculoRiscoRepository.save(calculoRiscoResidual);

            evento.setCalculoRiscoResidual(calculoRiscoResidual);
        }

        eventoRiscoRepository.save(evento);
    }

    private void saveEventoInEventoRisco(EventoRisco eventoRisco, String cpf) {
        Evento novoEvento = saveEvento(eventoRisco.getEvento().getDescricao(), cpf);

        eventoRisco = eventoRiscoRepository.findOne(eventoRisco.getId());
        eventoRisco.setEvento(novoEvento);
        eventoRiscoRepository.save(eventoRisco);
    }

    private Boolean eventoHasProcesso(EventoRisco eventoRisco, Evento evento) {
        List<Processo> processos = processoRepository.findByEventoId(eventoRisco.getEvento().getId());

        if (processos.size() <= 1) {
            evento.setDescricao(eventoRisco.getEvento().getDescricao());
            evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
            eventoRepository.save(evento);
            return true;
        } else {
            return false;
        }
    }

    private void saveCausaInEventoCausa(EventoCausa eventoCausa, String cpf) {
        Causa novaCausa = saveCausa(eventoCausa.getCausa().getDescricao(), cpf);

        eventoCausa = eventoCausaRepository.findOne(eventoCausa.getId());
        eventoCausa.setCausa(novaCausa);
        eventoCausaRepository.save(eventoCausa);
    }

    private Boolean causaHasProcesso(EventoCausa eventoCausa, Causa causa) {
        List<Processo> processos = processoRepository.findByCausaId(eventoCausa.getCausa().getId());

        if (processos.size() <= 1) {
            causa.setDescricao(eventoCausa.getCausa().getDescricao());
            causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
            causaRepository.save(causa);
            return true;
        } else {
            return false;
        }
    }

    private void saveConsequenciaInEventoConsequencia(EventoConsequencia eventoConsequencia, String cpf) {
        Consequencia novaConsequencia = saveConsequencia(eventoConsequencia.getConsequencia().getDescricao(), cpf);

        eventoConsequencia = eventoConsequenciaRepository.findOne(eventoConsequencia.getId());
        eventoConsequencia.setConsequencia(novaConsequencia);
        eventoConsequenciaRepository.save(eventoConsequencia);
    }

    private Boolean consequenciaHasProcesso(EventoConsequencia eventoConsequencia, Consequencia consequencia) {
        List<Processo> processos = processoRepository.findByConsequenciaId(eventoConsequencia.getConsequencia().getId());

        if (processos.size() <= 1) {
            consequencia.setDescricao(eventoConsequencia.getConsequencia().getDescricao());
            consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
            consequenciaRepository.save(consequencia);
            return true;
        } else {
            return false;
        }
    }

    private void saveControleInControleEvento(ControleEvento controleEvento, String cpf) {
        Controle novoControle = saveControle(controleEvento.getControle().getDescricao(), cpf);

        controleEvento = controleEventoRepository.findOne(controleEvento.getId());
        controleEvento.setControle(novoControle);
        controleEventoRepository.save(controleEvento);
    }

    private Boolean controleHasProcesso(ControleEvento controleEvento, Controle controle) {
        List<Processo> processos = processoRepository.findByControleId(controleEvento.getControle().getId());

        if (processos.size() <= 1) {
            controle.setDescricao(controleEvento.getControle().getDescricao());
            controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
            controleRepository.save(controle);
            return true;
        } else {
            return false;
        }
    }

    private void atualizarControleEvento(ControleEvento controleEvento, ControleEventoDTO controleEventoDTO) {
        ControleEvento novoControleEvento = controleEventoMapper.controleEventoDTOToControleEvento(controleEventoDTO);
        controleEvento.setDesenho(novoControleEvento.getDesenho());
        controleEvento.setOperacao(novoControleEvento.getOperacao());
        controleEventoRepository.save(controleEvento);
    }
}