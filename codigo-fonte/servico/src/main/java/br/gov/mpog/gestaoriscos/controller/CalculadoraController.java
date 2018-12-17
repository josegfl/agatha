package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoCalculadoraDTO;
import br.gov.mpog.gestaoriscos.servico.CalculadoraService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Calculadora.
 */
@RestController
@RequestMapping(value = "/calculadoras", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CalculadoraController{

    private final Logger log = LoggerFactory.getLogger(CalculadoraController.class);

    private final CalculadoraService service;

    @Autowired
    public CalculadoraController(CalculadoraService service) {
        this.service = service;
    }

    /**
     * PUT: Updates an existing calculadora.
     *
     * @param calculadoraDTO the calculadoraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calculadoraDTO,
     * or with status 400 (Bad Request) if the calculadoraDTO is not valid,
     * or with status 500 (Internal Server Error) if the calculadoraDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CalculadoraDTO> updateCalculadora(@Valid @RequestBody CalculadoraDTO calculadoraDTO) throws URISyntaxException{
        log.debug("REST request to update Calculadora : {}", calculadoraDTO);
        CalculadoraDTO result = service.save(calculadoraDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US027_1, calculadoraDTO.getId().toString())).body(result);
    }

    /**
     * GET: get all the calculadoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calculadoras in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CalculadoraDTO>> getAllCalculadoras() throws URISyntaxException{
        log.debug("REST request to get Calculadoras");
        List<CalculadoraDTO> results = service.findAll();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    /**
     * GET: get all the tipos calculadora.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calculadoras in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tipos")
    public ResponseEntity<List<TipoCalculadoraDTO>> getAllTiposCalculadora() throws URISyntaxException{
        log.debug("REST request to get tipos de Calculadora");
        List<TipoCalculadoraDTO> results = service.findAllTiposCalculadora();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }
}
