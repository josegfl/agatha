package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.DesenhoDTO;
import br.gov.mpog.gestaoriscos.servico.DesenhoService;
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
 * REST controller for managing Desenho.
 */
@RestController
@RequestMapping(value = "/desenhos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DesenhoController {

    private final Logger log = LoggerFactory.getLogger(DesenhoController.class);

    private static final String ENTITY_NAME = "desenho";

    private final DesenhoService service;

    @Autowired
    public DesenhoController(DesenhoService service) {
        this.service = service;
    }

    /**
     * POST: Create a new desenho.
     *
     * @param desenhoDTO the desenhoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new desenhoDTO, or with status 400 (Bad Request) if the desenho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DesenhoDTO> createDesenho(@Valid @RequestBody DesenhoDTO desenhoDTO) throws URISyntaxException {
        log.debug("REST request to save Desenho : {}", desenhoDTO);
        if (desenhoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new desenho cannot already have an ID")).body(null);
        } else if (service.verificarExistencia(desenhoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        DesenhoDTO result = service.save(desenhoDTO);
        return ResponseEntity.created(new URI("/api/desenhos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing desenho.
     *
     * @param desenhoDTO the desenhoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated desenhoDTO,
     * or with status 400 (Bad Request) if the desenhoDTO is not valid,
     * or with status 500 (Internal Server Error) if the desenhoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<DesenhoDTO> updateDesenho(@Valid @RequestBody DesenhoDTO desenhoDTO) throws URISyntaxException {
        log.debug("REST request to update Desenho : {}", desenhoDTO);
        if (service.verificarExistencia(desenhoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (desenhoDTO.getId() == null) {
            return createDesenho(desenhoDTO);
        }
        DesenhoDTO result = service.save(desenhoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, desenhoDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the desenhos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of desenhos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DesenhoDTO>> getAllDesenhos(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Desenhos");
        Page<DesenhoDTO> page = service.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/desenhos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" desenho.
     *
     * @param id the id of the desenhoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the desenhoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<DesenhoDTO> getDesenho(@PathVariable Long id) {
        log.debug("REST request to get Desenho : {}", id);
        DesenhoDTO desenhoDTO = service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(desenhoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" desenho.
     *
     * @param id the id of the desenhoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteDesenho(@PathVariable Long id) {
        log.debug("REST request to delete Desenho : {}", id);
        if (service.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else {
            service.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
        }
    }

    /**
     * GET get the "descricao" desenho.
     *
     * @param descricao the id of the desenhoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the desenhoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Desenho : {}", descricao);
        List<String> results = service.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
