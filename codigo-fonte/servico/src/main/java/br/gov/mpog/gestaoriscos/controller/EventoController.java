package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.servico.EventoService;
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
 * REST controller for managing Evento.
 */
@RestController
@RequestMapping(value = "/eventos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EventoController {

    private final Logger log = LoggerFactory.getLogger(EventoController.class);

    private static final String ENTITY_NAME = "evento";

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * POST: Create a new evento.
     *
     * @param eventoDTO the eventoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventoDTO, or with status 400 (Bad Request) if the evento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EventoDTO> createEvento(@Valid @RequestBody EventoDTO eventoDTO) throws URISyntaxException {
        log.debug("REST request to save Evento : {}", eventoDTO);
        if (eventoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new evento cannot already have an ID")).body(null);
        } else if (eventoService.verificarExistencia(eventoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        }
        EventoDTO result = eventoService.save(eventoDTO);
        return ResponseEntity.created(new URI("/api/eventos/" + result.getId())).headers(HeaderUtil.createAlert(Mensagens.US015_2, result.getId().toString())).body(result);
    }

    /**
     * PUT: Updates an existing evento.
     *
     * @param eventoDTO the eventoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventoDTO,
     * or with status 400 (Bad Request) if the eventoDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<EventoDTO> updateEvento(@Valid @RequestBody EventoDTO eventoDTO) throws URISyntaxException {
        log.debug("REST request to update Evento : {}", eventoDTO);
        if (eventoService.verificarExistencia(eventoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US015_1)).body(null);
        } else if (eventoDTO.getId() == null) {
            return createEvento(eventoDTO);
        }
        EventoDTO result = eventoService.save(eventoDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, eventoDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the eventos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EventoDTO>> getAllEventos(Pageable pageable, @RequestParam(value = "descricao", required = false) String descricao, @RequestParam(value = "status", required = false) Boolean status) throws URISyntaxException {
        log.debug("REST request to get a page of Eventos");
        Page<EventoDTO> page = eventoService.findAll(pageable, StringUtil.removerAcento(descricao), status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" evento.
     *
     * @param id the id of the eventoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable Long id) {
        log.debug("REST request to get Evento : {}", id);
        EventoDTO eventoDTO = eventoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" evento.
     *
     * @param id the id of the eventoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        log.debug("REST request to delete Evento : {}", id);
        if (eventoService.hasProcesso(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_1)).body(null);
        } else if (eventoService.hasTaxonomia(id)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.US020_5)).body(null);
        } else {
            eventoService.delete(id);
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
        List<String> results = eventoService.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
