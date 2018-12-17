package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Evento;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.EventoDTO;
import br.gov.mpog.gestaoriscos.repositorio.EventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaBaseOrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.servico.EventoService;
import br.gov.mpog.gestaoriscos.servico.mapper.EventoMapper;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Evento.
 */
@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);

    private final EventoRepository eventoRepository;

    private final TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio;

    private final ProcessoRepository processoRepository;

    private final EventoMapper eventoMapper;

    private final TaxonomiaRepository taxonomiaRepository;

    @Autowired
    public EventoServiceImpl(EventoRepository eventoRepository, TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio, ProcessoRepository processoRepository, EventoMapper eventoMapper, TaxonomiaRepository taxonomiaRepository) {
        this.eventoRepository = eventoRepository;
        this.taxonomiaBaseOrgaoCustomRepositorio = taxonomiaBaseOrgaoCustomRepositorio;
        this.processoRepository = processoRepository;
        this.eventoMapper = eventoMapper;
        this.taxonomiaRepository = taxonomiaRepository;
    }

    /**
     * Save a evento.
     *
     * @param eventoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventoDTO save(EventoDTO eventoDTO) {
        log.debug("Request to save Evento : {}", eventoDTO);
        Evento evento = eventoMapper.eventoDTOToEvento(eventoDTO);
        evento = eventoRepository.save(evento);
        evento.setSearch(StringUtil.removerAcento(evento.getDescricao()));
        return eventoMapper.eventoToEventoDTO(evento);
    }

    /**
     * Get all the eventos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoDTO> findAll(Pageable pageable, String descricao, Boolean status) {
        log.debug("Request to get all Eventos");
        Page<Evento> result;

        if (status != null) {
            result = eventoRepository.findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = eventoRepository.findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(evento -> eventoMapper.eventoToEventoDTO(evento));
    }

    /**
     * Get one evento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventoDTO findOne(Long id) {
        log.debug("Request to get Evento : {}", id);
        Evento evento = eventoRepository.findOne(id);
        EventoDTO eventoDTO = eventoMapper.eventoToEventoDTO(evento);
        return eventoDTO;
    }

    /**
     * Delete the  evento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evento : {}", id);
        eventoRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Evento com a mesma descrição
     *
     * @param eventoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(EventoDTO eventoDTO) {
        Evento result = eventoRepository.findBySearchIgnoreCaseAndOrgaoIdIsNull(eventoDTO.getDescricao());

        return !(result == null || result.getId().equals(eventoDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseOrgaoCustomRepositorio.searchByDescricao(descricao, "Evento");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<Processo> processos = processoRepository.findByEventoId(id);
        return !processos.isEmpty();
    }

    @Override
    public Boolean hasTaxonomia(Long id) {
        List<Taxonomia> result = taxonomiaRepository.findByEventoId(id);
        return !result.isEmpty();
    }
}
