package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.servico.ControleService;
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
 * REST controller for managing Controle.
 */
@RestController
@RequestMapping(value = "/controles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ControleController {

    private final Logger log = LoggerFactory.getLogger(ControleController.class);

    private static final String ENTITY_NAME = "controle";

    private final ControleService controleService;

    @Autowired
    public ControleController(ControleService controleService) {
        this.controleService = controleService;
    }

    /**
     * POST: Create a new controle.
     *
     * @param controleDTO the controleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new controleDTO, or with status 400 (Bad Request) if the controle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ControleDTO> createControle(@Valid @RequestBody ControleDTO controleDTO) throws URISyntaxException {
        log.debug("REST request to save Controle : {}", controleDTO);
        if (controleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new controle cannot already have an ID")).body(null);
        } else if (controleService.verificarExistencia(controleDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        ControleDTO result = controleService.save(controleDTO);
        return ResponseEntity.created(new URI("/api/controles/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing controle.
     *
     * @param controleDTO the controleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated controleDTO,
     * or with status 400 (Bad Request) if the controleDTO is not valid,
     * or with status 500 (Internal Server Error) if the controleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ControleDTO> updateControle(@Valid @RequestBody ControleDTO controleDTO) throws URISyntaxException {
        log.debug("REST request to update Controle : {}", controleDTO);
        if (controleService.verificarExistencia(controleDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (controleDTO.getId() == null) {
            return createControle(controleDTO);
        }
        ControleDTO result = controleService.save(controleDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, controleDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the controles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of controles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ControleDTO>> getAllControles(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Controles");
        Page<ControleDTO> page = controleService.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/controles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" controle.
     *
     * @param id the id of the controleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controleDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ControleDTO> getControle(@PathVariable Long id) {
        log.debug("REST request to get Controle : {}", id);
        ControleDTO controleDTO = controleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(controleDTO));
    }

    /**
     * DELETE  /:id : delete the "id" controle.
     *
     * @param id the id of the controleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteControle(@PathVariable Long id) {
        log.debug("REST request to delete Controle : {}", id);
        if (controleService.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else if (controleService.hasTaxonomia(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_5)).body(null);
        } else {
            controleService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
        }
    }

    /**
     * GET get the "descricao" controle.
     *
     * @param descricao the id of the controleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controleDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Controle : {}", descricao);
        List<String> results = controleService.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
