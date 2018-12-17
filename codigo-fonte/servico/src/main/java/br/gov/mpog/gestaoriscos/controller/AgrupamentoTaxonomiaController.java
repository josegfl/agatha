package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.AgrupamentoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.servico.AgrupamentoTaxonomiaService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Taxonomia.
 */
@RestController
@RequestMapping(value = "/agrupamentotaxonomias", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AgrupamentoTaxonomiaController {

    private final Logger log = LoggerFactory.getLogger(AgrupamentoTaxonomiaController.class);

    private final AgrupamentoTaxonomiaService service;

    @Autowired
    public AgrupamentoTaxonomiaController(AgrupamentoTaxonomiaService service) {
        this.service = service;
    }

    /**
     * POST: Aprova uma taxonomia.
     *
     * @param agrupamentoTaxonomiaDTO to create
     * @return the ResponseEntity with status 200
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AgrupamentoTaxonomiaDTO> agruparTaxonomia(@Valid @RequestBody AgrupamentoTaxonomiaDTO agrupamentoTaxonomiaDTO) throws URISyntaxException {
        log.debug("REST request to aprovar Taxonomia : {}", agrupamentoTaxonomiaDTO);
        AgrupamentoTaxonomiaDTO result = service.save(agrupamentoTaxonomiaDTO);
        return ResponseEntity.created(new URI("/api/agrupamentotaxonomias/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * GET: get all the taxonomias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxonomias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AgrupamentoTaxonomiaDTO>> getAllTaxonomias(Pageable pageable,
                                                                          @RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao,
                                                                          @RequestParam(value = "statusId", required = false) Long tipoId,
                                                                          @RequestParam(value = "dtInicio", required = false) Long inicio,
                                                                          @RequestParam(value = "dtFim", required = false) Long fim) throws URISyntaxException {
        log.debug("REST request to get a page of Taxonomias");
        Page<AgrupamentoTaxonomiaDTO> page = service.findAll(pageable, descricao, inicio, fim, tipoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxonomias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tipos")
    public ResponseEntity<List<TipoTaxonomiaDTO>> getTiposTaxonomia() {
        log.debug("REST request to get all tipos de taxonomia");
        List<TipoTaxonomiaDTO> result = service.findAllTiposTaxonomia();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET get the "descricao" taxonomia.
     *
     * @param descricao the id of the taxonomiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxonomiaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao) {
        log.debug("REST request to get Taxonomia : {}", descricao);
        List<String> results = service.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    /**
     * GET get the "descricao" taxonomia.
     *
     * @param descricao the id of the taxonomiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxonomiaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchtaxonomia")
    public ResponseEntity<List<TaxonomiaDTO>> getTaxonomiaBySearch(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao, @RequestParam(value = "tipoId", required = false) Long tipoId) {
        log.debug("REST request to get Taxonomia : {}", descricao);
        List<TaxonomiaDTO> results = service.getTaxonomiaBySearch(descricao, tipoId);
        return ResponseEntity.ok().body(results);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/eventos")
    public ResponseEntity<List<EventoDTO>> getEventosBySearch(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao) {
        log.debug("REST request to get Evento : {}", descricao);
        List<EventoDTO> results = service.getEventosBySearch(descricao);
        return ResponseEntity.ok().body(results);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/causas")
    public ResponseEntity<List<CausaDTO>> getCausasBySearch(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao) {
        log.debug("REST request to get Causa : {}", descricao);
        List<CausaDTO> results = service.getCausasBySearch(descricao);
        return ResponseEntity.ok().body(results);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/consequencias")
    public ResponseEntity<List<ConsequenciaDTO>> getConsequenciasBySearch(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao) {
        log.debug("REST request to get Consequencia : {}", descricao);
        List<ConsequenciaDTO> results = service.getConsequenciasBySearch(descricao);
        return ResponseEntity.ok().body(results);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/controles")
    public ResponseEntity<List<ControleDTO>> getControlesBySearch(@RequestParam(value = AnnotationStringUtil.DESCRICAO, required = false) String descricao) {
        log.debug("REST request to get Controle : {}", descricao);
        List<ControleDTO> results = service.getControlesBySearch(descricao);
        return ResponseEntity.ok().body(results);
    }
}
