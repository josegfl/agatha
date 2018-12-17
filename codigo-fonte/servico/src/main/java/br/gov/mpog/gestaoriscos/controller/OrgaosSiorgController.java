package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.modelo.dto.CargaStatusDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoConsultaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import br.gov.mpog.gestaoriscos.servico.OrgaoServico;
import br.gov.mpog.gestaoriscos.servico.OrgaosCargaServico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/siorg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrgaosSiorgController {

    private final OrgaoServico orgaoServico;

    private final OrgaosCargaServico servico;

    private final Logger logger = LoggerFactory.getLogger(OrgaosSiorgController.class);

    @Autowired
    public OrgaosSiorgController(OrgaoServico orgaoServico, OrgaosCargaServico servico) {
        this.orgaoServico = orgaoServico;
        this.servico = servico;
    }

    @RequestMapping(value = "/filtrar", method = RequestMethod.GET)
    public ResponseEntity<List<OrgaoDTO>> filtrarOrgaos(@RequestParam(value = "nomeOrgao", required = true) String nomeOrgao) {
        return new ResponseEntity<>(orgaoServico.filtrar(nomeOrgao), HttpStatus.OK);
    }

    @RequestMapping(value = "/orgao/limitado-por-id-categoria", method = RequestMethod.POST)
    public ResponseEntity<List<OrgaoDTO>> getUnidadePeloIdLimitadoPorIdCategoria(@RequestBody OrgaoConsultaDTO orgaoConsultaDTO) {
        return ResponseEntity.ok(orgaoServico.getUnidadePeloIdLimitadoPorIdCategoria(orgaoConsultaDTO));
    }

    @RequestMapping(value = "/orgao/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<OrgaoDTO>> getUnidadePeloId(@PathVariable("id") Long id) {
        return obterOrgaos(id);
    }

    @RequestMapping(value = "/orgao", method = RequestMethod.GET)
    public ResponseEntity<List<OrgaoDTO>> getUnidadesRaizes() {
        return obterOrgaos(null);
    }

    private ResponseEntity<List<OrgaoDTO>> obterOrgaos(final Long id) {
        HttpStatus httpStatus;
        List<OrgaoDTO> orgaoDTOS = null;
        try {
            orgaoDTOS = orgaoServico.listarOrgaos(id);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(orgaoDTOS, httpStatus);
    }

    /**
     * Uso apenas para testes de carga imediata, sem ter a necessecidade de alterar o agendamento
     * Será removido quando sair a release...
     */
    @Deprecated
    @RequestMapping(value = "/importar", method = RequestMethod.GET)
    public ResponseEntity<CargaStatusDTO> testarImportacao() throws Exception {
        CargaStatusDTO cargaStatusDTO;
        cargaStatusDTO = servico.importarEstruturaOrganizacional();
        return new ResponseEntity<>(cargaStatusDTO, HttpStatus.OK);
    }

}
