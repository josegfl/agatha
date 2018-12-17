package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.servico.CausaService;
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
 * REST controller for managing Causa.
 */
@RestController
@RequestMapping(value = "/causas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CausaController {

    private final Logger log = LoggerFactory.getLogger(CausaController.class);

    private static final String ENTITY_NAME = "causa";

    private final CausaService causaService;

    @Autowired
    public CausaController(CausaService causaService) {
        this.causaService = causaService;
    }

    /**
     * POST: Create a new causa.
     *
     * @param causaDTO the causaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new causaDTO, or with status 400 (Bad Request) if the causa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CausaDTO> createCausa(@Valid @RequestBody CausaDTO causaDTO) throws URISyntaxException {
        log.debug("REST request to save Causa : {}", causaDTO);
        if (causaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new causa cannot already have an ID")).body(null);
        } else if (causaService.verificarExistencia(causaDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        CausaDTO result = causaService.save(causaDTO);
        return ResponseEntity.created(new URI("/api/causas/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing causa.
     *
     * @param causaDTO the causaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated causaDTO,
     * or with status 400 (Bad Request) if the causaDTO is not valid,
     * or with status 500 (Internal Server Error) if the causaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CausaDTO> updateCausa(@Valid @RequestBody CausaDTO causaDTO) throws URISyntaxException {
        log.debug("REST request to update Causa : {}", causaDTO);
        if (causaService.verificarExistencia(causaDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (causaDTO.getId() == null) {
            return createCausa(causaDTO);
        }
        CausaDTO result = causaService.save(causaDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, causaDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the causas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of causas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CausaDTO>> getAllCausas(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Causas");
        Page<CausaDTO> page = causaService.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/causas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" causa.
     *
     * @param id the id of the causaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the causaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<CausaDTO> getCausa(@PathVariable Long id) {
        log.debug("REST request to get Causa : {}", id);
        CausaDTO causaDTO = causaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(causaDTO));
    }

    /**
     * DELETE  /:id : delete the "id" causa.
     *
     * @param id the id of the causaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteCausa(@PathVariable Long id) {
        log.debug("REST request to delete Causa : {}", id);
        if (causaService.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else if (causaService.hasTaxonomia(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_5)).body(null);
        } else {
            causaService.delete(id);
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
        List<String> results = causaService.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
