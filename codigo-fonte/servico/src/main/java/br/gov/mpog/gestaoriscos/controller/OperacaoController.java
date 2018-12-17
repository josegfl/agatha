package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.OperacaoDTO;
import br.gov.mpog.gestaoriscos.servico.OperacaoService;
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
 * REST controller for managing Operacao.
 */
@RestController
@RequestMapping(value = "/operacaos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OperacaoController {

    private final Logger log = LoggerFactory.getLogger(OperacaoController.class);

    private static final String ENTITY_NAME = "operacao";

    private final OperacaoService service;

    @Autowired
    public OperacaoController(OperacaoService service) {
        this.service = service;
    }

    /**
     * POST: Create a new operacao.
     *
     * @param operacaoDTO the operacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operacaoDTO, or with status 400 (Bad Request) if the operacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OperacaoDTO> createOperacao(@Valid @RequestBody OperacaoDTO operacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Operacao : {}", operacaoDTO);
        if (operacaoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operacao cannot already have an ID")).body(null);
        } else if (service.verificarExistencia(operacaoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        OperacaoDTO result = service.save(operacaoDTO);
        return ResponseEntity.created(new URI("/api/operacaos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing operacao.
     *
     * @param operacaoDTO the operacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operacaoDTO,
     * or with status 400 (Bad Request) if the operacaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the operacaoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<OperacaoDTO> updateOperacao(@Valid @RequestBody OperacaoDTO operacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Operacao : {}", operacaoDTO);
        if (service.verificarExistencia(operacaoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (operacaoDTO.getId() == null) {
            return createOperacao(operacaoDTO);
        }
        OperacaoDTO result = service.save(operacaoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, operacaoDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the operacaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operacaos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OperacaoDTO>> getAllOperacaos(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Operacaos");
        Page<OperacaoDTO> page = service.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operacaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" operacao.
     *
     * @param id the id of the operacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operacaoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<OperacaoDTO> getOperacao(@PathVariable Long id) {
        log.debug("REST request to get Operacao : {}", id);
        OperacaoDTO operacaoDTO = service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operacaoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" operacao.
     *
     * @param id the id of the operacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteOperacao(@PathVariable Long id) {
        log.debug("REST request to delete Operacao : {}", id);
        if (service.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else {
            service.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
        }
    }

    /**
     * GET get the "descricao" operacao.
     *
     * @param descricao the id of the operacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operacaoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchdescricao")
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Operacao : {}", descricao);
        List<String> results = service.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
