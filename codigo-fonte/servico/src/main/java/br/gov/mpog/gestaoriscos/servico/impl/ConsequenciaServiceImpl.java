package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Consequencia;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.ConsequenciaDTO;
import br.gov.mpog.gestaoriscos.repositorio.ConsequenciaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaBaseOrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.servico.ConsequenciaService;
import br.gov.mpog.gestaoriscos.servico.mapper.ConsequenciaMapper;
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
 * Service Implementation for managing Consequencia.
 */
@Service
@Transactional
public class ConsequenciaServiceImpl implements ConsequenciaService {

    private final Logger log = LoggerFactory.getLogger(ConsequenciaServiceImpl.class);

    private final ConsequenciaRepository consequenciaRepository;

    private final TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio;

    private final ProcessoRepository processoRepository;

    private final ConsequenciaMapper consequenciaMapper;

    private final TaxonomiaRepository taxonomiaRepository;

    @Autowired
    public ConsequenciaServiceImpl(ConsequenciaRepository consequenciaRepository, TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio, ProcessoRepository processoRepository, ConsequenciaMapper consequenciaMapper, TaxonomiaRepository taxonomiaRepository) {
        this.consequenciaRepository = consequenciaRepository;
        this.taxonomiaBaseOrgaoCustomRepositorio = taxonomiaBaseOrgaoCustomRepositorio;
        this.processoRepository = processoRepository;
        this.consequenciaMapper = consequenciaMapper;
        this.taxonomiaRepository = taxonomiaRepository;
    }

    /**
     * Save a consequencia.
     *
     * @param consequenciaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsequenciaDTO save(ConsequenciaDTO consequenciaDTO) {
        log.debug("Request to save Consequencia : {}", consequenciaDTO);
        Consequencia consequencia = consequenciaMapper.consequenciaDTOToConsequencia(consequenciaDTO);
        consequencia.setSearch(StringUtil.removerAcento(consequencia.getDescricao()));
        consequencia = consequenciaRepository.save(consequencia);
        return consequenciaMapper.consequenciaToConsequenciaDTO(consequencia);
    }

    /**
     * Get all the consequencias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsequenciaDTO> findAll(Pageable pageable, String descricao, Boolean status) {
        log.debug("Request to get all Consequencias");
        Page<Consequencia> result;

        if (status != null) {
            result = consequenciaRepository.findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = consequenciaRepository.findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(consequencia -> consequenciaMapper.consequenciaToConsequenciaDTO(consequencia));
    }

    /**
     * Get one consequencia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsequenciaDTO findOne(Long id) {
        log.debug("Request to get Consequencia : {}", id);
        Consequencia consequencia = consequenciaRepository.findOne(id);
        ConsequenciaDTO consequenciaDTO = consequenciaMapper.consequenciaToConsequenciaDTO(consequencia);
        return consequenciaDTO;
    }

    /**
     * Delete the  consequencia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consequencia : {}", id);
        consequenciaRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Consequencia com a mesma descrição
     *
     * @param consequenciaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(ConsequenciaDTO consequenciaDTO) {
        Consequencia result = consequenciaRepository.findBySearchIgnoreCaseAndOrgaoIdIsNull(StringUtil.removerAcento(consequenciaDTO.getDescricao()));
        return !(result == null || result.getId().equals(consequenciaDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseOrgaoCustomRepositorio.searchByDescricao(descricao, "Consequencia");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<Processo> processos = processoRepository.findByConsequenciaId(id);
        return !processos.isEmpty();
    }

    @Override
    public Boolean hasTaxonomia(Long id) {
        List<Taxonomia> result = taxonomiaRepository.findByConsequenciaId(id);
        return !result.isEmpty();
    }
}
