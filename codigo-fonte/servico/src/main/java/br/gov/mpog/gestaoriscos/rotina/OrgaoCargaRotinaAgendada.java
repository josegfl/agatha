package br.gov.mpog.gestaoriscos.rotina;

import br.gov.mpog.gestaoriscos.modelo.dto.CargaStatusDTO;
import br.gov.mpog.gestaoriscos.servico.OrgaosCargaServico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Rotina de agendamento para importação dos orgaos do SIORG
 */
@Component
public class OrgaoCargaRotinaAgendada {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private OrgaosCargaServico servico;

    private final Logger logger = LoggerFactory.getLogger(OrgaoCargaRotinaAgendada.class);

    private void log(final String msg) {
        logger.info(msg);
    }

    @Scheduled(cron = "${siorg.carga.agendamento.cron}")
    public void executar() {
        CargaStatusDTO cargaStatusDTO = new CargaStatusDTO();
        log("" + "Rotina para importação de dados do SIORG... Início: " + dateFormat.format(new Date()));
        try {
            cargaStatusDTO = servico.importarEstruturaOrganizacional();
        } catch (Exception e) {
            log("" + "Rotina para importação de dados do SIORG... " + ". Erro: " + e.getMessage());
            logger.debug(e.getMessage(), e);
        } finally {
            log("" + "Rotina para importação de dados do SIORG... Fim: " + dateFormat.format(new Date()) + ". Inseridos  : " + cargaStatusDTO.getInsercoes() + ". Atualizados: " + cargaStatusDTO.getAtualizacoes() + ". Tempo Gasto: " + cargaStatusDTO.getTempoGasto());
        }
    }
}