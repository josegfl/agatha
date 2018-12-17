package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.servico.ConsequenciaService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import br.gov.mpog.gestaoriscos.util.StringUtil;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Consequencia.
 */
@RestController
@RequestMapping(value = "/consequencias", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConsequenciaController {

    private final Logger log = LoggerFactory.getLogger(ConsequenciaController.class);

    private static final String ENTITY_NAME = "consequencia";

    private final ConsequenciaService consequenciaService;

    @Autowired
    public ConsequenciaController(ConsequenciaService consequenciaService) {
        this.consequenciaService = consequenciaService;
    }

    /**
     * POST: Create a new consequencia.
     *
     * @param consequenciaDTO the consequenciaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consequenciaDTO, or with status 400 (Bad Request) if the consequencia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ConsequenciaDTO> createConsequencia(@Valid @RequestBody ConsequenciaDTO consequenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Consequencia : {}", consequenciaDTO);
        if (consequenciaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new consequencia cannot already have an ID")).body(null);
        } else if (consequenciaService.verificarExistencia(consequenciaDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        ConsequenciaDTO result = consequenciaService.save(consequenciaDTO);
        return ResponseEntity.created(new URI("/api/consequencias/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing consequencia.
     *
     * @param consequenciaDTO the consequenciaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consequenciaDTO,
     * or with status 400 (Bad Request) if the consequenciaDTO is not valid,
     * or with status 500 (Internal Server Error) if the consequenciaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ConsequenciaDTO> updateConsequencia(@Valid @RequestBody ConsequenciaDTO consequenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Consequencia : {}", consequenciaDTO);
        if (consequenciaService.verificarExistencia(consequenciaDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (consequenciaDTO.getId() == null) {
            return createConsequencia(consequenciaDTO);
        }
        ConsequenciaDTO result = consequenciaService.save(consequenciaDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, consequenciaDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the consequencias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consequencias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ConsequenciaDTO>> getAllConsequencias(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Consequencias");
        Page<ConsequenciaDTO> page = consequenciaService.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consequencias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" consequencia.
     *
     * @param id the id of the consequenciaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consequenciaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ConsequenciaDTO> getConsequencia(@PathVariable Long id) {
        log.debug("REST request to get Consequencia : {}", id);
        ConsequenciaDTO consequenciaDTO = consequenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consequenciaDTO));
    }

    /**
     * DELETE  /:id : delete the "id" consequencia.
     *
     * @param id the id of the consequenciaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteConsequencia(@PathVariable Long id) {
        log.debug("REST request to delete Consequencia : {}", id);
        if (consequenciaService.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else if (consequenciaService.hasTaxonomia(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_5)).body(null);
        } else {
            consequenciaService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
        }
    }

    /**
     * GET get the "descricao" causa.
     *
     * @param descricao the id of the causaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the causaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Causa : {}", descricao);
        List<String> results = consequenciaService.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
