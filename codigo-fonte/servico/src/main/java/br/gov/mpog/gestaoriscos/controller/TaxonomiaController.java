package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaContainerDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.servico.TaxonomiaService;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Taxonomia.
 */
@RestController
@RequestMapping(value = "/taxonomias", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaxonomiaController {

    private final Logger log = LoggerFactory.getLogger(TaxonomiaController.class);

    private final TaxonomiaService service;

    @Autowired
    public TaxonomiaController(TaxonomiaService service) {
        this.service = service;
    }

    /**
     * POST: Aprova uma taxonomia.
     *
     * @param containerDTO to aprove
     * @return the ResponseEntity with status 200
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST, value = "aprovar")
    public ResponseEntity<TaxonomiaDTO> aprovarTaxonomia(@Valid @RequestBody TaxonomiaContainerDTO containerDTO) throws URISyntaxException {
        log.debug("REST request to aprovar Taxonomia : {}", containerDTO);
        service.aprovarTaxonomia(containerDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US026_2, "")).body(null);
    }

    /**
     * POST: Reprova uma taxonomia.
     *
     * @param containerDTO to reprova
     * @return the ResponseEntity with status 200
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST, value = "reprovar")
    public ResponseEntity<TaxonomiaDTO> reprovarTaxonomia(@Valid @RequestBody TaxonomiaContainerDTO containerDTO) throws URISyntaxException {
        log.debug("REST request to aprovar Taxonomia : {}", containerDTO);
        service.reprovarTaxonomia(containerDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US026_3, "")).body(null);
    }

    /**
     * GET: get all the taxonomias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxonomias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TaxonomiaDTO>> getAllTaxonomias(Pageable pageable,
                                                               @RequestParam(value = "orgao", required = false) String orgao,
                                                               @RequestParam(value = "descricao", required = false) String descricao,
                                                               @RequestParam(value = "tipoId", required = false) Long tipoId,
                                                               @RequestParam(value = "dtInicio", required = false) Long inicio,
                                                               @RequestParam(value = "dtFim", required = false) Long fim) throws URISyntaxException {
        log.debug("REST request to get a page of Taxonomias");
        Page<TaxonomiaDTO> page = service.findAll(pageable, descricao, orgao, inicio, fim, tipoId);
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
    public ResponseEntity<List<String>> searchByDescricao(@RequestParam(value = "descricao", required = false) String descricao) {
        log.debug("REST request to get Taxonomia : {}", descricao);
        List<String> results = service.searchByDescricao(descricao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    /**
     * GET get the "descricao" órgão.
     *
     * @param nome the id of the causaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the causaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchorgao")
    public ResponseEntity<List<String>> searchByNome(@RequestParam(value = "nome", required = false) String nome) {
        log.debug("REST request to get órgão : {}", nome);
        List<String> results = service.searchOrgaoByNome(nome);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/secretariabyperfil")
    public ResponseEntity<OrgaoDTO> getSecretariaByPerfil(@RequestParam(value = AnnotationStringUtil.CPF, required = false) String cpf) {
        log.debug("REST request to get órgão by perfil: {}", cpf);
        return new ResponseEntity<>(service.getSecretariaByPerfil(cpf), HttpStatus.OK);
    }
}
