package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Evento.
 */
public interface EventoService {

    /**
     * Save a evento.
     *
     * @param eventoDTO the entity to save
     * @return the persisted entity
     */
    EventoDTO save(EventoDTO eventoDTO);

    /**
     * Get all the eventos.
     *
     * @param pageable the pagination information, descricao and status
     * @return the list of entities
     */
    Page<EventoDTO> findAll(Pageable pageable, String descricao, Boolean status);

    /**
     * Get the "id" evento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EventoDTO findOne(Long id);

    /**
     * Delete the "id" evento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Verifica se existe alguma Evento com a mesma descrição
     *
     * @param eventoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    Boolean verificarExistencia(EventoDTO eventoDTO);

    List<String> searchByDescricao(String descricao);

    Boolean hasProcesso(Long id);

    Boolean hasTaxonomia(Long id);
}
