package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
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
import br.gov.mpog.gestaoriscos.servico.ProcessoService;
import br.gov.mpog.gestaoriscos.util.AnnotationStringUtil;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Processo.
 */
@RestController
@RequestMapping(value = "/processos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProcessoController {

    private final Logger log = LoggerFactory.getLogger(ProcessoController.class);

    private static final String ENTITY_NAME = "processo";

    private final ProcessoService service;

    @Autowired
    public ProcessoController(ProcessoService service) {
        this.service = service;
    }

    /**
     * POST: Create a new processo.
     *
     * @param processoDTO the processoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new processoDTO, or with status 400 (Bad Request) if the processo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProcessoDTO> createProcesso(@Valid @RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to save Processo : {}", processoDTO);
        if (processoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new processo cannot already have an ID")).body(null);
        }
        ProcessoDTO result = service.save(processoDTO);
        return ResponseEntity.created(new URI("/api/processos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing processo.
     *
     * @param processoDTO the processoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated processoDTO,
     * or with status 400 (Bad Request) if the processoDTO is not valid,
     * or with status 500 (Internal Server Error) if the processoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ProcessoDTO> updateProcesso(@Valid @RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to update Processo : {}", processoDTO);
        if (processoDTO.getId() == null) {
            return createProcesso(processoDTO);
        }
        ProcessoDTO result = service.save(processoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, processoDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the processos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of processos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProcessoListagemDTO>> getAllProcessos(Pageable pageable,
                                                             @RequestParam(value = AnnotationStringUtil.CPF, required = false) String cpf,
                                                             @RequestParam(value = "orgao", required = false) String orgao,
                                                             @RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao,
                                                             @RequestParam(value = "statusId", required = false) Long statusId,
                                                             @RequestParam(value = "dtInicio", required = false) Long inicio,
                                                             @RequestParam(value = "dtFim", required = false) Long fim) throws URISyntaxException {
        log.debug("REST request to get a page of Processos");
        Page<ProcessoListagemDTO> page = service.findAll(pageable, cpf, orgao, descricao, statusId, inicio, fim);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/processos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" processo.
     *
     * @param id the id of the processoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the processoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ProcessoDTO> getProcesso(@PathVariable Long id) {
        log.debug("REST request to get Processo : {}", id);
        ProcessoDTO processoDTO = service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" processo.
     *
     * @param id the id of the processoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        log.debug("REST request to delete Processo : {}", id);
        service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the processoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/analistas")
    public ResponseEntity<List<UsuarioDTO>> findAllUsuarios() {
        log.debug("REST request to get all usuarios with perfil 'analista'");
        List<UsuarioDTO> result = service.findAllAnalistas();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the processoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/gestores")
    public ResponseEntity<List<UsuarioDTO>> findAllGestores() {
        log.debug("REST request to get all usuarios with perfil 'gestor'");
        List<UsuarioDTO> result = service.findAllGestores();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the processoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tiposmatriz")
    public ResponseEntity<List<TipoMatrizDTO>> findAllTiposMatriz() {
        log.debug("REST request to get all tipos matriz");
        List<TipoMatrizDTO> result = service.findAllTiposMatriz();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the orgaosDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/orgaosbycpf")
    public ResponseEntity<OrgaosDTO> getOrgaosByCpf(@RequestParam(value = AnnotationStringUtil.CPF, required = false) String cpf) {
        log.debug("REST request to get orgao and secretaria from usuario");
        OrgaosDTO result = service.findOrgaosByCPF(cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the processoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/permissao")
    public ResponseEntity<PermissaoProcessoDTO> getPermissoes(@RequestParam(value = AnnotationStringUtil.CPF, required = false) String cpf) {
        log.debug("REST request to get permissao from usuario");
        PermissaoProcessoDTO result = service.findPermissaoByCPF(cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET: get all the eventos de risco.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of processos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eventosrisco")
    public ResponseEntity<List<EventoRiscoDTO>> getAllEventosRisco(Pageable pageable, @RequestParam(value = "processoId") Long processoId) throws URISyntaxException {
        log.debug("REST request to get a page of Processos");
        Page<EventoRiscoDTO> page = service.findAllEventosRisco(pageable, processoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/processos/eventosrisco");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET: get all the eventos de risco.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of processos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eventosriscowithoutpage/{id}")
    public ResponseEntity<List<EventoRiscoDTO>> getAllEventosRisco(@PathVariable("id") Long processoId) throws URISyntaxException {
        log.debug("REST request to get a evento risco");
        List<EventoRiscoDTO> result = service.findAllEventosRisco(processoId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET  /:id : get the "id" evento risco.
     *
     * @param id the id of the eventoRiscoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventoRiscoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eventosrisco/{id}")
    public ResponseEntity<EventoRiscoDTO> getEventoRisco(@PathVariable Long id) {
        log.debug("REST request to get Evento de Risco : {}", id);
        EventoRiscoDTO eventoRiscoDTO = service.findEventoRisco(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventoRiscoDTO));
    }

    /**
     * POST: Create a new eventoRisco.
     *
     * @param eventoRiscoDTO the eventoRiscoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventoRiscoDTO, or with status 400 (Bad Request) if the eventoRisco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco")
    public ResponseEntity<EventoRiscoDTO> createEventoRisco(@Valid @RequestBody EventoRiscoDTO eventoRiscoDTO) throws URISyntaxException {
        log.debug("REST request to save Evento de Risco : {}", eventoRiscoDTO);
        if (eventoRiscoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new evento risco cannot already have an ID")).body(null);
        }
        EventoRiscoDTO result = service.saveEventoRisco(eventoRiscoDTO, eventoRiscoDTO.getCpf());
        return ResponseEntity.created(new URI("/api/processos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing eventoRisco.
     *
     * @param eventoRiscoDTO the eventoRiscoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventoRiscoDTO,
     * or with status 400 (Bad Request) if the eventoRiscoDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventoRiscoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/eventosrisco")
    public ResponseEntity<EventoRiscoDTO> updateEventoRisco(@Valid @RequestBody EventoRiscoDTO eventoRiscoDTO) throws URISyntaxException {
        log.debug("REST request to update Evento de Risco : {}", eventoRiscoDTO);
        if (eventoRiscoDTO.getId() == null) {
            return createEventoRisco(eventoRiscoDTO);
        }
        EventoRiscoDTO result = service.saveEventoRisco(eventoRiscoDTO, eventoRiscoDTO.getCpf());
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, eventoRiscoDTO.getId().toString())).body(result);
    }

    /**
     * DELETE  /:id : delete the "id" eventoRisco.
     *
     * @param id the id of the eventoRiscoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/eventosrisco/{id}")
    public ResponseEntity<Void> deleteEventoRisco(@PathVariable Long id) {
        log.debug("REST request to delete Processo : {}", id);
        service.deleteEventoRisco(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the eventoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eventos")
    public ResponseEntity<List<EventoDTO>> getEventos(@RequestParam(value = AnnotationStringUtil.DESCRICAO) String descricao, @RequestParam(value = AnnotationStringUtil.CPF) String cpf) {
        log.debug("REST request to get eventos");
        List<EventoDTO> result = service.findEventoByDescricaoAndCPF(descricao, cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the causaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/causas")
    public ResponseEntity<List<CausaDTO>> getCausas(@RequestParam(value = AnnotationStringUtil.DESCRICAO) String descricao, @RequestParam(value = AnnotationStringUtil.CPF) String cpf) {
        log.debug("REST request to get causas");
        List<CausaDTO> result = service.findCausaByDescricaoAndCPF(descricao, cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * ""
     *
     * @return the ResponseEntity with status 200 (OK) and with body the consequenciaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/consequencias")
    public ResponseEntity<List<ConsequenciaDTO>> getConsequencias(@RequestParam(value = AnnotationStringUtil.DESCRICAO) String descricao, @RequestParam(value = AnnotationStringUtil.CPF) String cpf) {
        log.debug("REST request to get consequencias");
        List<ConsequenciaDTO> result = service.findConsequenciaByDescricaoAndCPF(descricao, cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/categorias")
    public ResponseEntity<List<CategoriaDTO>> getCategorias() {
        log.debug("REST request to get categorias");
        List<CategoriaDTO> result = service.findAllCategoria();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the naturezaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/naturezas")
    public ResponseEntity<List<NaturezaDTO>> getNaturezas() {
        log.debug("REST request to get naturezas");
        List<NaturezaDTO> result = service.findAllNatureza();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/evento/verificar")
    public ResponseEntity<PerfilDTO> verificarEvento(@Valid @RequestBody EventoRiscoDTO eventoRiscoDTO) {
        log.debug("REST request to verify evento");
        PerfilDTO result = service.verificarEvento(eventoRiscoDTO);
        if (result != null) {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/evento/atualizar")
    public ResponseEntity<EventoDTO> atualizarEvento(@Valid @RequestBody EventoDTO eventoDTO) {
        log.debug("REST request to updates evento");
        EventoDTO result = service.atualizarEvento(eventoDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/evento/substituir")
    public ResponseEntity<EventoRiscoDTO> substituirEvento(@Valid @RequestBody EventoRiscoDTO eventoRiscoDTO) {
        log.debug("REST request to create a new evento");
        EventoRiscoDTO result = service.substituirEvento(eventoRiscoDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/causa/verificar")
    public ResponseEntity<PerfilDTO> verificarCausa(@Valid @RequestBody EventoCausaDTO dto) {
        log.debug("REST request to verify causa");
        PerfilDTO result = service.verificarCausa(dto);
        if (result != null) {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/causa/atualizar")
    public ResponseEntity<CausaDTO> atualizarCausa(@Valid @RequestBody CausaDTO dto) {
        log.debug("REST request to updates causa");
        CausaDTO result = service.atualizarCausa(dto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/causa/substituir")
    public ResponseEntity<EventoCausaDTO> substituirCausa(@Valid @RequestBody EventoCausaDTO dto) {
        log.debug("REST request to create a new causa");
        EventoCausaDTO result = service.substituirCausa(dto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/consequencia/verificar")
    public ResponseEntity<PerfilDTO> verificarConsequencia(@Valid @RequestBody EventoConsequenciaDTO dto) {
        log.debug("REST request to verify consequencia");
        PerfilDTO result = service.verificarConsequencia(dto);
        if (result != null) {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/consequencia/atualizar")
    public ResponseEntity<ConsequenciaDTO> atualizarConsequencia(@Valid @RequestBody ConsequenciaDTO dto) {
        log.debug("REST request to updates consequencia");
        ConsequenciaDTO result = service.atualizarConsequencia(dto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/consequencia/substituir")
    public ResponseEntity<EventoConsequenciaDTO> substituirConsequencia(@Valid @RequestBody EventoConsequenciaDTO dto) {
        log.debug("REST request to create a new consequencia");
        EventoConsequenciaDTO result = service.substituirConsequencia(dto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchorgao")
    public ResponseEntity<List<String>> searchByNome(@RequestParam(value = "nome", required = false) String nome) {
        log.debug("REST request to get orgaos by nome : {}", nome);
        List<String> results = service.searchOrgaoByNome(nome);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao) {
        log.debug("REST request to get processo by descricao : {}", descricao);
        List<String> results = service.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    public ResponseEntity<List<StatusProcessoDTO>> getStatus() {
        log.debug("REST request to get statusProcesso: {}");
        List<StatusProcessoDTO> results = service.getStatus();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadfile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ArquivoAnexoDTO>> uploadOneFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String nome) throws IOException {
        log.debug("REST request to upload a file: {}");
        MultipartFile[] files = {file};
        String[] nomes = {nome};
        return uploadFile(files, nomes);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadfileEmpty", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ArquivoAnexoDTO>> uploadOneFileEmpty() throws IOException {
        log.debug("REST request to upload a file: {}");
        MultipartFile[] files = {};
        String[] nomes = {};
        return uploadFile(files, nomes);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ArquivoAnexoDTO>> uploadFile(@RequestPart("files") MultipartFile[] arquivos, @RequestPart("names") String[] nomes) throws IOException {
        log.debug("REST request to upload a file: {}");
        List<ArquivoAnexoDTO> results = service.uploadFile(arquivos, nomes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download/{id}")
    public ResponseEntity<byte[]> downloadAnexo(@PathVariable("id") Long id) {
        ArquivoAnexo arquivo = service.getAnexoById(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("charset", "utf-8");
        responseHeaders.setContentLength(arquivo.getArquivo().length);
        responseHeaders.set("Content-disposition", "attachment; filename=" + arquivo.getNomeArquivo());

        return new ResponseEntity<>(arquivo.getArquivo(), responseHeaders, HttpStatus.OK);
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the eventoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/controles")
    public ResponseEntity<List<ControleDTO>> getControles(@RequestParam(value = AnnotationStringUtil.DESCRICAO) String descricao, @RequestParam(value = AnnotationStringUtil.CPF) String cpf) {
        log.debug("REST request to get controles");
        List<ControleDTO> result = service.findControleByDescricaoAndCPF(descricao, cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the desenhoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/desenhos")
    public ResponseEntity<List<DesenhoDTO>> getDesenhos() {
        log.debug("REST request to get desenhos");
        List<DesenhoDTO> result = service.findAllDesenhos();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the operacaoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/operacoes")
    public ResponseEntity<List<OperacaoDTO>> getOperacoes() {
        log.debug("REST request to get operacoes");
        List<OperacaoDTO> result = service.findAllOperacoes();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/controle/verificar")
    public ResponseEntity<PerfilDTO> verificarControle(@Valid @RequestBody ControleEventoDTO dto) {
        log.debug("REST request to verify controle");
        PerfilDTO result = service.verificarControle(dto);
        if (result != null) {
            return ResponseUtil.wrapOrNotFound(Optional.of(result));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/controle/atualizar")
    public ResponseEntity<ControleDTO> atualizarControle(@Valid @RequestBody ControleDTO dto) {
        log.debug("REST request to updates controle");
        ControleDTO result = service.atualizarControle(dto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/eventosrisco/controle/substituir")
    public ResponseEntity<ControleEventoDTO> substituirControle(@Valid @RequestBody ControleEventoDTO dto) {
        log.debug("REST request to create a new controle");
        ControleEventoDTO result = service.substituirControle(dto);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/calculadorainerente/{id}")
    public ResponseEntity<CalculadoraDTO> getCalculadoraInerente(@PathVariable("id") Long id) {
        log.debug("REST request to calculadora inerente from processo");
        CalculadoraDTO result = service.getCalculadoraInerenteByProcessoId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/calculadoraresidual/{id}")
    public ResponseEntity<CalculadoraDTO> getCalculadoraResidual(@PathVariable("id") Long id) {
        log.debug("REST request to calculadora residual from processo");
        CalculadoraDTO result = service.getCalculadoraResidualByProcessoId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/salvarcalculo")
    public ResponseEntity<IdentificacaoDTO> salvarCalculo(@Valid @RequestBody IdentificacaoDTO identificacaoDTO) {
        log.debug("REST request to save calculos");
        service.salvarCalculo(identificacaoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, "")).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/avaliacao/ignorar/{id}")
    public ResponseEntity<Boolean> getIgnorarRiscoInerenteByProcesso(@PathVariable("id") Long id) {
        log.debug("REST request to get ignorar risco inerente by processo Id");
        Boolean result = service.getIgnorarRiscoInerenteByProcesso(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/avaliacao/alterarignorar")
    public ResponseEntity<Boolean> alterarIgnorarRiscoInerente(@Valid @RequestBody ProcessoDTO dto) {
        log.debug("REST request to update ignorar risco inerente");
        Boolean result = service.alterarIgnorarRiscoInerente(dto.getId());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/taxonomia/evento")
    public ResponseEntity<TaxonomiaDTO> salvarTaxonomiaEvento(@Valid @RequestBody EventoDTO eventoDTO) {
        log.debug("REST request to save taxonomia de evento", eventoDTO);
        TaxonomiaDTO result = service.salvarTaxonomiaEvento(eventoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US026_1, "")).body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/taxonomia/causa")
    public ResponseEntity<TaxonomiaDTO> salvarTaxonomiaCausa(@Valid @RequestBody CausaDTO causaDTO) {
        log.debug("REST request to save taxonomia de causa", causaDTO);
        TaxonomiaDTO result = service.salvarTaxonomiaCausa(causaDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US026_1, "")).body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/taxonomia/consequencia")
    public ResponseEntity<TaxonomiaDTO> salvarTaxonomiaConsequencia(@Valid @RequestBody ConsequenciaDTO consequenciaDTO) {
        log.debug("REST request to save taxonomia de consequencia", consequenciaDTO);
        TaxonomiaDTO result = service.salvarTaxonomiaConsequencia(consequenciaDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US026_1, "")).body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/taxonomia/controle")
    public ResponseEntity<TaxonomiaDTO> salvarTaxonomiaControle(@Valid @RequestBody ControleDTO controleDTO) {
        log.debug("REST request to save taxonomia de controle", controleDTO);
        TaxonomiaDTO result = service.salvarTaxonomiaControle(controleDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US026_1, "")).body(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tiposresposta")
    public ResponseEntity<List<RespostaRiscoDTO>> getTiposResposta() {
        log.debug("REST request to get all tipos de resposta ao risco");
        List<RespostaRiscoDTO> result = service.getTiposResposta();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tiposcontrole")
    public ResponseEntity<List<TipoControleDTO>> getTiposControle() {
        log.debug("REST request to get all tipos de controle ao risco");
        List<TipoControleDTO> result = service.getTiposControle();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/objetivoscontrole")
    public ResponseEntity<List<ObjetivoControleDTO>> getObjetivosControle() {
        log.debug("REST request to get all objetivos de controle ao risco");
        List<ObjetivoControleDTO> result = service.getObjetivosControle();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/macroprocesso")
    public ResponseEntity<List<MacroprocessoDTO>> getMacroprocesso() {
        log.debug("REST request to get macroprocesso");
        List<MacroprocessoDTO> result = service.findAllMacroprocesso();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/avaliacao/risco-inerente/verifica/{id}")
    public ResponseEntity<Boolean> verificaRiscoInerente(@PathVariable Long id) {
        log.debug("REST request to verify a Risco Inerente by ProcessoId : {}", id);
        return new ResponseEntity<Boolean>(service.verificaRiscoInerente(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/avaliacao/controle-evento/verifica/{id}")
    public ResponseEntity<Boolean> verificaControleEvento(@PathVariable Long id) {
        log.debug("REST request to verify a Controle Evento by ProcessoId : {}", id);
        return new ResponseEntity<Boolean>(service.verificaControleEvento(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/impacto-calculadora/alterar-status")
    public ResponseEntity alterarStatusImpactoCalculadora(@Valid @RequestBody CalculadoraDTO calculadoraDTO) throws URISyntaxException {
        log.debug("REST request to update impacto calculadora status : {}", calculadoraDTO);
        service.alterarStatusImpactoCalculadora(calculadoraDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/impacto-calculadora/alterar-status/{processoId}")
    public ResponseEntity alterarStatusImpactoCalculadoras(@PathVariable Long processoId, @Valid @RequestBody CalculadoraDTO calculadoraDTO) throws URISyntaxException {
        log.debug("REST request to update impacto calculadoras status : {}", processoId, calculadoraDTO);
        service.alterarStatusImpactoCalculadoras(processoId, calculadoraDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/resposta-risco/{eventoRiscoId}")
    public ResponseEntity saveRespostaRisco(@PathVariable("eventoRiscoId") Long eventoRiscoId, @Valid @RequestBody EventoRiscoDTO eventoRiscoDTO) throws URISyntaxException {
        log.debug("REST request to update evento risco : {}", eventoRiscoId, eventoRiscoDTO);
        service.saveRespostaRisco(eventoRiscoId, eventoRiscoDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plano-controle/{eventoRiscoId}")
    public ResponseEntity<EventoRiscoDTO> findEventoWithControles(@PathVariable("eventoRiscoId") Long eventoRiscoId) throws URISyntaxException {
        log.debug("REST request to get evento risco with all controles: {}", eventoRiscoId);
        return new ResponseEntity<>(service.findEventoWithControles(eventoRiscoId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/plano-controle")
    public ResponseEntity<PlanoControleDTO> createPlanoControle(@Valid @RequestBody PlanoControleDTO planoControleDTO) throws URISyntaxException {
        log.debug("REST request to create plano controle : {}", planoControleDTO);
        return new ResponseEntity<>(service.savePlanoControle(planoControleDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/plano-controle/{planoControleId}")
    public ResponseEntity<PlanoControleDTO> updatePlanoControle(@PathVariable("planoControleId") Long planoControleId, @Valid @RequestBody PlanoControleDTO planoControleDTO) throws URISyntaxException {
        log.debug("REST request to update plano controle : {}", planoControleId, planoControleDTO);
        planoControleDTO.setId(planoControleId);
        return new ResponseEntity<>(service.savePlanoControle(planoControleDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/plano-controle/{planoControleId}")
    public ResponseEntity deletePlanoControle(@PathVariable("planoControleId") Long planoControleId) throws URISyntaxException {
        log.debug("REST request to delete plano controle : {}", planoControleId);
        service.deletePlanoControle(planoControleId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/etapa/{processoId}")
    public ResponseEntity<ProcessoEtapaDTO> getProcessoEtapa(@PathVariable("processoId") Long processoId) throws URISyntaxException {
        log.debug("REST request to get etapas do processo: {}", processoId);
        return new ResponseEntity<>(service.getProcessoEtapa(processoId), HttpStatus.OK);
    }

    /**
     * @return the ResponseEntity with status 200 (OK) and with body the decisaoProcessoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/decisoes")
    public ResponseEntity<List<DecisaoProcessoDTO>> getDecisoes() {
        log.debug("REST request to get decisoes");
        List<DecisaoProcessoDTO> result = service.findAllDecisoes();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/validar-processo")
    public ResponseEntity validarProcesso(@Valid @RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to update processo : {}", processoDTO);
        service.validarProcesso(processoDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
