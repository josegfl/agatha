package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaContainerDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TaxonomiaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoTaxonomiaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxonomia.
 */
public interface TaxonomiaService{

    /**
     * Aprova uma taxonomia.
     *
     * @param containerDTO the entity to save
     * @return the persisted entity
     */
    void aprovarTaxonomia(TaxonomiaContainerDTO containerDTO);

    /**
     * Reprova uma taxonomia.
     *
     * @param containerDTO the entity to save
     * @return the persisted entity
     */
    void reprovarTaxonomia(TaxonomiaContainerDTO containerDTO);

    /**
     * Get all the taxonomias.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<TaxonomiaDTO> findAll(Pageable pageable, String descricao, String orgao, Long inicio, Long fim, Long tipoTaxonomiaId);

    List<TipoTaxonomiaDTO> findAllTiposTaxonomia();

    List<String> searchByDescricao(String descricao);

    List<String> searchOrgaoByNome(String nome);

    OrgaoDTO getSecretariaByPerfil(String cpf);
}
