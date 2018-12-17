package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.AgrupamentoTaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxonomia.
 */
public interface AgrupamentoTaxonomiaService{

    /**
     * Cria um agrupamento de taxonomia.
     *
     * @param agrupamentoTaxonomiaDTO the entity to save
     * @return the persisted entity
     */
    AgrupamentoTaxonomiaDTO save(AgrupamentoTaxonomiaDTO agrupamentoTaxonomiaDTO);

    /**
     * Get all the taxonomias.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<AgrupamentoTaxonomiaDTO> findAll(Pageable pageable, String descricao, Long inicio, Long fim, Long tipoId);

    List<String> searchByDescricao(String descricao);

    List<TipoTaxonomiaDTO> findAllTiposTaxonomia();

    List<TaxonomiaDTO> getTaxonomiaBySearch(String descricao, Long tipoId);

    List<EventoDTO> getEventosBySearch(String descricao);

    List<CausaDTO> getCausasBySearch(String descricao);

    List<ConsequenciaDTO> getConsequenciasBySearch(String descricao);

    List<ControleDTO> getControlesBySearch(String descricao);
}
