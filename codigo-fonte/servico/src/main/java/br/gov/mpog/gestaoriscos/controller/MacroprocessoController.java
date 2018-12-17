package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.MacroprocessoDTO;
import br.gov.mpog.gestaoriscos.servico.MacroprocessoService;
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
 * REST controller for managing Macroprocesso.
 */
@RestController
@RequestMapping(value = "/macroprocessos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MacroprocessoController {

    private final Logger log = LoggerFactory.getLogger(MacroprocessoController.class);

    private static final String ENTITY_NAME = "macroprocesso";

    private final MacroprocessoService macroprocessoService;

    @Autowired
    public MacroprocessoController(MacroprocessoService macroprocessoService) {
        this.macroprocessoService = macroprocessoService;
    }

    /**
     * POST: Create a new macroprocesso.
     *
     * @param macroprocessoDTO the macroprocessoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new macroprocessoDTO, or with status 400 (Bad Request) if the macroprocesso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MacroprocessoDTO> createMacroprocesso(@Valid @RequestBody MacroprocessoDTO macroprocessoDTO) throws URISyntaxException {
        log.debug("REST request to save Macroprocesso : {}", macroprocessoDTO);
        if (macroprocessoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new macroprocesso cannot already have an ID")).body(null);
        } else if (macroprocessoService.verificarExistencia(macroprocessoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        MacroprocessoDTO result = macroprocessoService.save(macroprocessoDTO);
        return ResponseEntity.created(new URI("/api/macroprocessos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing macroprocesso.
     *
     * @param macroprocessoDTO the macroprocessoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated macroprocessoDTO,
     * or with status 400 (Bad Request) if the macroprocessoDTO is not valid,
     * or with status 500 (Internal Server Error) if the macroprocessoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<MacroprocessoDTO> updateMacroprocesso(@Valid @RequestBody MacroprocessoDTO macroprocessoDTO) throws URISyntaxException {
        log.debug("REST request to update Macroprocesso : {}", macroprocessoDTO);
        if (macroprocessoService.verificarExistencia(macroprocessoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (macroprocessoDTO.getId() == null) {
            return createMacroprocesso(macroprocessoDTO);
        }
        MacroprocessoDTO result = macroprocessoService.save(macroprocessoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, macroprocessoDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the macroprocessos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of macroprocessos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MacroprocessoDTO>> getAllMacroprocessos(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Macroprocessos");
        Page<MacroprocessoDTO> page = macroprocessoService.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/macroprocessos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" macroprocesso.
     *
     * @param id the id of the macroprocessoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the macroprocessoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<MacroprocessoDTO> getMacroprocesso(@PathVariable Long id) {
        log.debug("REST request to get Macroprocesso : {}", id);
        MacroprocessoDTO macroprocessoDTO = macroprocessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(macroprocessoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" macroprocesso.
     *
     * @param id the id of the macroprocessoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteMacroprocesso(@PathVariable Long id) {
        log.debug("REST request to delete Macroprocesso : {}", id);
        if (macroprocessoService.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else {
            macroprocessoService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
        }
    }

    /**
     * GET get the "descricao" macroprocesso.
     *
     * @param descricao the id of the macroprocessoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the macroprocessoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Macroprocesso : {}", descricao);
        List<String> results = macroprocessoService.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}