package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.controller.util.PaginationUtil;
import br.gov.mpog.gestaoriscos.controller.util.ResponseUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoContainerDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.PermissaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.servico.PermissaoService;
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
 * REST controller for managing Permissao.
 */
@RestController
@RequestMapping(value = "/permissaos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PermissaoController {

    private final Logger log = LoggerFactory.getLogger(PermissaoController.class);

    private static final String ENTITY_NAME = "permissao";

    private final PermissaoService permissaoService;

    @Autowired
    public PermissaoController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    /**
     * GET: get all the Usuarios by nome
     *
     * @return the ResponseEntity with status 200 (OK) and the list of usuarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET, value = "/usuarios")
    public ResponseEntity<List<String>> searchUsuarioByNome(@RequestParam(value = "nome", required = false) String nome) throws URISyntaxException {
        log.debug("REST request to get a page of Usuarios");
        List<String> result = permissaoService.searchUsuarioByNome(nome);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET: get all the Perfils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of perfils in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET, value = "/perfils")
    public ResponseEntity<List<PerfilDTO>> getAllPerfil() throws URISyntaxException {
        log.debug("REST request to get a page of Perfils");
        List<PerfilDTO> perfilDTOs = permissaoService.findAllPerfils();
        return new ResponseEntity<>(perfilDTOs, HttpStatus.OK);
    }

    /**
     * POST: Create a new permissao.
     *
     * @param permissaoContainerDTO the permissaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new permissaoDTO, or with status 400 (Bad Request) if the permissao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<PermissaoDTO>> createPermissao(@Valid @RequestBody PermissaoContainerDTO permissaoContainerDTO) throws URISyntaxException {
        log.debug("REST request to save uma lista de Permissao : {}", permissaoContainerDTO);
        permissaoService.saveList(permissaoContainerDTO);
        return ResponseEntity.created(new URI("/api/permissaos/")).headers(HeaderUtil.createAlert(Mensagens.US015_2, "")).body(null);
    }

    /**
     * PUT: Updates an existing permissao.
     *
     * @param permissaoDTO the permissaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated permissaoDTO,
     * or with status 400 (Bad Request) if the permissaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the permissaoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<PermissaoDTO> updatePermissao(@Valid @RequestBody PermissaoDTO permissaoDTO) throws URISyntaxException {
        log.debug("REST request to update Permissao : {}", permissaoDTO);
        if (permissaoService.verificarExistencia(permissaoDTO)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", Mensagens.BASIS_18666_1)).body(null);
        } else {
            PermissaoDTO result = permissaoService.save(permissaoDTO);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, "")).body(result);
        }
    }

    /**
     * GET: get all the permissaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of permissaos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UsuarioDTO>> getAllPermissao(Pageable pageable, @RequestParam(value = "usuario", required = false) String usuario, @RequestParam(value = "orgao", required = false) Long orgao, @RequestParam(value = "perfil", required = false) Long perfilId) throws URISyntaxException {
        log.debug("REST request to get a page of Permissaos");
        Page<UsuarioDTO> page = permissaoService.findAll(usuario, perfilId, orgao, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/permissaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /:id : get the "id" permissao.
     *
     * @param id the id of the permissaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the permissaoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<PermissaoDTO> getPermissao(@PathVariable Long id) {
        log.debug("REST request to get Permissao : {}", id);
        PermissaoDTO permissaoDTO = permissaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(permissaoDTO));
    }

    /**
     * DELETE  /:id : delete the "id" permissao.
     *
     * @param id the id of the permissaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deletePermissao(@PathVariable Long id) {
        log.debug("REST request to delete Permissao : {}", id);
        permissaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_4, id.toString())).build();
    }

    /**
     * GET get the "descricao" causa.
     *
     * @param nome the id of the causaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the causaDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchorgao")
    public ResponseEntity<List<String>> searchByNome(@RequestParam(value = "nome", required = false) String nome) {
        log.debug("REST request to get Causa : {}", nome);
        List<String> results = permissaoService.searchOrgaoByNome(nome);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }


    /**
     * @return the ResponseEntity with status 200 (OK) and with body the orgaosDTO, or with status 404 (Not Found)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/permissoesbycpf")
    public ResponseEntity<List<PermissaoDTO>> getPermissoesByCPF(@RequestParam(value = AnnotationStringUtil.CPF, required = false) String cpf) {
        log.debug("REST request to get orgao and secretaria from usuario");
        List<PermissaoDTO> result = permissaoService.getPermissoesByCPF(cpf);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
