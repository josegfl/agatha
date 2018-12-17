package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.dto.CargaStatusDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EstruturaOrganizacionalDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoCargaDTO;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCargaRepositorio;
import br.gov.mpog.gestaoriscos.servico.OrgaosCargaServico;
import br.gov.mpog.gestaoriscos.util.TimeWatcher;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * "Client" de acesso remoto ao Siorg
 */
@Service
public class OrgaosCargaServicoImpl implements OrgaosCargaServico {

    @Value("${siorg.carga.url}")
    private String siorgURL;

    @Autowired
    private OrgaoCargaRepositorio repositorioCarga;

    private EstruturaOrganizacionalDTO estruturaOrganizacional;

    private LinkedList<Long> existentes;

    private TimeWatcher timeWatcher = TimeWatcher.start();

    private int inseridos = 0;
    private int atualizados = 0;

    private final Logger logger = LoggerFactory.getLogger(OrgaosCargaServicoImpl.class);

    private void log(final String msg) {
        logger.debug(msg);
    }

    public CargaStatusDTO importarEstruturaOrganizacional() throws Exception {
        TimeWatcher timeWatcher = TimeWatcher.start();

        //-> obtem os dados do siorg
        obterDadosSiorg();

        //-> extrai a estrutura
        List<List<Orgao>> listagens = extrairListagensDeOrgaos();

        //-> lista TODOS os existentes já carregados (ou já inseridos)
        existentes = repositorioCarga.listarExistentes();

        //-> salva os orgaos
        salvarListagens(listagens);

        //-> libera memoria
        limparListagens(listagens);

        timeWatcher.end();
        String msg = "TEMPO TOTAL PARA FAZER A GARGA: " + timeWatcher.elapsedTimeStr();
        log(msg);
        return new CargaStatusDTO(inseridos, atualizados, msg, timeWatcher.elapsedTimeStr());
    }

    private void limparListagens(List<List<Orgao>> listagens) {
        existentes.clear();
        existentes = null;
    }

    @SuppressWarnings("unchecked")
    private List<Orgao> extrairOrgaos() {
        final List<Orgao> todosOrgaos = new ArrayList<>(1);
        final List<OrgaoCargaDTO> orgaoCargaDTOS = estruturaOrganizacional.getUnidades();
        final HashMap<Long, OrgaoCargaDTO> mapaOrgaosDTOCarga = new HashMap<>(1);
        for (OrgaoCargaDTO orgaoCargaDTO : orgaoCargaDTOS) {
            mapaOrgaosDTOCarga.put(orgaoCargaDTO.getCodigoUnidadeAsLong(), orgaoCargaDTO);
        }
        mapaOrgaosDTOCarga.values().parallelStream().forEach(orgaoCargaDTO -> {
            OrgaoCargaDTO pai = mapaOrgaosDTOCarga.get(orgaoCargaDTO.getCodigoUnidadePaiAsLong());
            orgaoCargaDTO.setPai(pai);
        });
        orgaoCargaDTOS.parallelStream().forEach(OrgaoCargaDTO::definirNivel);
        Collections.sort(orgaoCargaDTOS);
        for (OrgaoCargaDTO orgaoCargaDTO : orgaoCargaDTOS) {
            todosOrgaos.add(orgaoCargaDTO.orgao());
        }
        orgaoCargaDTOS.clear();
        mapaOrgaosDTOCarga.clear();
        estruturaOrganizacional.getUnidades().clear();
        estruturaOrganizacional = null;
        return todosOrgaos;
    }

    private List<List<Orgao>> extrairListagensDeOrgaos() throws IOException {
        timeWatcher.reset();
        List<Orgao> todosOrgaos = extrairOrgaos();
        int NUM_UNIDADES_POR_TRANSACAO = 128;
        List<List<Orgao>> listagens = Lists.partition(todosOrgaos, NUM_UNIDADES_POR_TRANSACAO);
        timeWatcher.end();
        log("Tempo gasto para Extrair: " + timeWatcher.elapsedTimeStr());
        return listagens;
    }

    private void obterDadosSiorg() throws Exception {
        log("Buscando o JSON de " + siorgURL);
        timeWatcher.reset();
        RestTemplate restTemplate = new RestTemplate();
        estruturaOrganizacional = restTemplate.getForEntity(siorgURL, EstruturaOrganizacionalDTO.class).getBody();
        log("Tempo gasto no download: " + timeWatcher.elapsedTimeStr());
    }

    private void salvarListagens(List<List<Orgao>> listasOrgaos) {
        log("Iniciando carga ...");
        timeWatcher.reset();
        listasOrgaos.stream().sequential().forEach(this::salvarOrgaos);
        timeWatcher.end();
        log("Tempo gasto na carga: " + timeWatcher.elapsedTimeStr());
    }

    private void salvarOrgaos(final List<Orgao> orgaos) {
        List<Orgao> orgaosParaInserir = new ArrayList<>(1);
        List<Orgao> orgaosParaAtualizar = new ArrayList<>(1);
        for (Orgao orgao : orgaos) {
            if (existentes.contains(orgao.getId())) {
                orgaosParaAtualizar.add(orgao);
            } else {
                orgaosParaInserir.add(orgao);
            }
        }
        repositorioCarga.inserir(orgaosParaInserir);
        repositorioCarga.atualizar(orgaosParaAtualizar);
        inseridos += orgaosParaInserir.size();
        atualizados += orgaosParaAtualizar.size();
    }

}
