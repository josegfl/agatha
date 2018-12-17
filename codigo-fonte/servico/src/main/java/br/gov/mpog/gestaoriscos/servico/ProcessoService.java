package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.ArquivoAnexo;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Service Interface for managing Processo.
 */
public interface ProcessoService{

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save
     * @return the persisted entity
     */
    ProcessoDTO save(ProcessoDTO processoDTO);

    /**
     * Get all the processos.
     *  @param pageable  the pagination information, descricao and status
     * @param orgao
     * @param descricao
     * @param statusId
     * @param dtInicio
     * @param dtFim     @return the list of entities
     */
    Page<ProcessoListagemDTO> findAll(Pageable pageable, String cpf, String orgao, String descricao, Long statusId, Long dtInicio, Long dtFim);

    /**
     * Get the "id" processo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProcessoDTO findOne(Long id);

    /**
     * Delete the "id" processo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<UsuarioDTO> findAllAnalistas();

    List<TipoMatrizDTO> findAllTiposMatriz();

    List<UsuarioDTO> findAllGestores();

    PermissaoProcessoDTO findPermissaoByCPF(String usuarioId);

    Page<EventoRiscoDTO> findAllEventosRisco(Pageable pageable, Long processoId);

    EventoRiscoDTO findEventoRisco(Long id);

    EventoRiscoDTO saveEventoRisco(EventoRiscoDTO eventoRiscoDTO, String cpf);

    void deleteEventoRisco(Long id);

    List<EventoDTO> findEventoByDescricaoAndCPF(String descricao, String cpf);

    List<CausaDTO> findCausaByDescricaoAndCPF(String descricao, String cpf);

    List<ConsequenciaDTO> findConsequenciaByDescricaoAndCPF(String descricao, String cpf);

    List<CategoriaDTO> findAllCategoria();

    List<NaturezaDTO> findAllNatureza();

    PerfilDTO verificarEvento(EventoRiscoDTO eventoRiscoDTO);

    PerfilDTO verificarCausa(EventoCausaDTO eventoCausaDTO);

    EventoDTO atualizarEvento(EventoDTO eventoDTO);

    CausaDTO atualizarCausa(CausaDTO causaDTO);

    EventoRiscoDTO substituirEvento(EventoRiscoDTO eventoRiscoDTO);

    EventoCausaDTO substituirCausa(EventoCausaDTO eventoCausaDTO);

    PerfilDTO verificarConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO);

    ConsequenciaDTO atualizarConsequencia(ConsequenciaDTO consequenciaDTO);

    EventoConsequenciaDTO substituirConsequencia(EventoConsequenciaDTO eventoConsequenciaDTO);

    OrgaosDTO findOrgaosByCPF(String cpf);

    List<String> searchOrgaoByNome(String nome);

    List<String> searchByDescricao(String descricao);

    List<StatusProcessoDTO> getStatus();

    List<ArquivoAnexoDTO> uploadFile(MultipartFile[] arquivos, String[] nomes) throws IOException;

    ArquivoAnexo getAnexoById(Long id);

    List<ControleDTO> findControleByDescricaoAndCPF(String descricao, String cpf);

    List<DesenhoDTO> findAllDesenhos();

    List<OperacaoDTO> findAllOperacoes();

    PerfilDTO verificarControle(ControleEventoDTO controleEventoDTO);

    ControleDTO atualizarControle(ControleDTO controleDTO);

    ControleEventoDTO substituirControle(ControleEventoDTO controleEventoDTO);

    CalculadoraDTO getCalculadoraInerenteByProcessoId(Long processoId);

    CalculadoraDTO getCalculadoraResidualByProcessoId(Long processoId);

    List<EventoRiscoDTO> findAllEventosRisco(Long processoId);

    Void salvarCalculo(IdentificacaoDTO identificacaoDTO);

    Boolean getIgnorarRiscoInerenteByProcesso(Long processoId);

    Boolean alterarIgnorarRiscoInerente(Long processoId);

    TaxonomiaDTO salvarTaxonomiaEvento(EventoDTO eventoDTO);

    TaxonomiaDTO salvarTaxonomiaCausa(CausaDTO causaDTO);

    TaxonomiaDTO salvarTaxonomiaConsequencia(ConsequenciaDTO consequenciaDTO);

    TaxonomiaDTO salvarTaxonomiaControle(ControleDTO controleDTO);

    List<RespostaRiscoDTO> getTiposResposta();

    List<TipoControleDTO> getTiposControle();

    List<ObjetivoControleDTO> getObjetivosControle();

    List<MacroprocessoDTO> findAllMacroprocesso();

    Boolean verificaRiscoInerente(Long processoId);

    Boolean verificaControleEvento(Long processoId);

    void alterarStatusImpactoCalculadora(CalculadoraDTO calculadoraDTO);

    void alterarStatusImpactoCalculadoras(Long processoId, CalculadoraDTO calculadoraDTO);

    void saveRespostaRisco(Long eventoRiscoId, EventoRiscoDTO eventoRiscoDTO);

    EventoRiscoDTO findEventoWithControles(Long eventoRiscoId);

    PlanoControleDTO savePlanoControle(PlanoControleDTO planoControleDTO);

    void deletePlanoControle(Long planoControleId);

    ProcessoEtapaDTO getProcessoEtapa(Long processoId);

    List<DecisaoProcessoDTO> findAllDecisoes();

    void validarProcesso(ProcessoDTO processoDTO);
}
