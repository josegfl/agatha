package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.OperacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Operacao.
 */
public interface OperacaoService{

    /**
     * Save a operacao.
     *
     * @param operacaoDTO the entity to save
     * @return the persisted entity
     */
    OperacaoDTO save(OperacaoDTO operacaoDTO);

    /**
     * Get all the operacaos.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<OperacaoDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" operacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OperacaoDTO findOne(Long id);

    /**
     * Delete the "id" operacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Operacao com a mesma descrição
     *
     * @param operacaoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(OperacaoDTO operacaoDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);
}
