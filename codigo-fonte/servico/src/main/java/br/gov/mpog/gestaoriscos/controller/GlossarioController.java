package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.GlossarioDTO;
import br.gov.mpog.gestaoriscos.servico.GlossarioService;
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
 * REST controller for managing Glossario.
 */
@RestController
@RequestMapping(value = "/glossarios", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GlossarioController {

    private final Logger log = LoggerFactory.getLogger(GlossarioController.class);

    private static final String ENTITY_NAME = "glossario";

    private final GlossarioService glossarioService;

    @Autowired
    public GlossarioController(GlossarioService glossarioService) {
        this.glossarioService = glossarioService;
    }

    /**
     * POST: Create a new glossario.
     *
     * @param glossarioDTO the glossarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossarioDTO, or with status 400 (Bad Request) if the glossario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GlossarioDTO> createGlossario(@Valid @RequestBody GlossarioDTO glossarioDTO) throws URISyntaxException {
        log.debug("REST request to save Glossario : {}", glossarioDTO);
        if (glossarioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new glossario cannot already have an ID")).body(null);
        } else if (glossarioService.verificarExistencia(glossarioDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        GlossarioDTO result = glossarioService.save(glossarioDTO);
        return ResponseEntity.created(new URI("/api/glossarios/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing glossario.
     *
     * @param glossarioDTO the glossarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossarioDTO,
     * or with status 400 (Bad Request) if the glossarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the glossarioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<GlossarioDTO> updateGlossario(@Valid @RequestBody GlossarioDTO glossarioDTO) throws URISyntaxException {
        log.debug("REST request to update Glossario : {}", glossarioDTO);
        if (glossarioService.verificarExistencia(glossarioDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (glossarioDTO.getId() == null) {
            return createGlossario(glossarioDTO);
        }
        GlossarioDTO result = glossarioService.save(glossarioDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, glossarioDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the glossarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GlossarioDTO>> getAllGlossarios(Pageable pageable,
                                                               @RequestParam(value = "termo", required = false) String termo,
                                                               @RequestParam(value = "descricao", required = false) String descricao,
                                                               @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Glossarios");
        Page<GlossarioDTO> page = glossarioService.findAll(pageable, StringUtil.removerAcento(termo), StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" glossario.
     *
     * @param id the id of the glossarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossarioDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<GlossarioDTO> getGlossario(@PathVariable Long id) {
        log.debug("REST request to get Glossario : {}", id);
        GlossarioDTO glossarioDTO = glossarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossarioDTO));
    }

    /**
     * DELETE  /:id : delete the "id" glossario.
     *
     * @param id the id of the glossarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteGlossario(@PathVariable Long id) {
        log.debug("REST request to delete Glossario : {}", id);
        glossarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
    }

    /**
     * GET get the "termo" glossario.
     *
     * @param termo the id of the glossarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossarioDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchtermo")
    public ResponseEntity<List<String>> searchByTermo(@RequestParam(value = "termo", required = false) String termo) {
        log.debug("REST request to get Glossario : {}", termo);
        List<String> results = glossarioService.searchByTermo(termo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    /**
     * GET get the "descricao" glossario.
     *
     * @param descricao the id of the glossarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossarioDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Glossario : {}", descricao);
        List<String> results = glossarioService.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    /**
     * GET get the "descricao" glossario.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the glossarioDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findall")
    public ResponseEntity<List<GlossarioDTO>> findAllGlossarios() {
        log.debug("REST request to get All Glossario : {}");
        List<GlossarioDTO> results = glossarioService.findAll();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
