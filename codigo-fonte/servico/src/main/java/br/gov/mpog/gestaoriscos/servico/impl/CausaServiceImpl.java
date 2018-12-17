package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Causa;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.CausaDTO;
import br.gov.mpog.gestaoriscos.repositorio.CausaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaBaseOrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.servico.CausaService;
import br.gov.mpog.gestaoriscos.servico.mapper.CausaMapper;
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
 * Service Implementation for managing Causa.
 */
@Service
@Transactional
public class CausaServiceImpl implements CausaService {

    private final Logger log = LoggerFactory.getLogger(CausaServiceImpl.class);

    private final CausaRepository causaRepository;

    private final TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio;

    private final ProcessoRepository processoRepository;

    private final CausaMapper causaMapper;

    private final TaxonomiaRepository taxonomiaRepository;

    @Autowired
    public CausaServiceImpl(CausaRepository causaRepository, TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio, ProcessoRepository processoRepository, CausaMapper causaMapper, TaxonomiaRepository taxonomiaRepository) {
        this.causaRepository = causaRepository;
        this.taxonomiaBaseOrgaoCustomRepositorio = taxonomiaBaseOrgaoCustomRepositorio;
        this.processoRepository = processoRepository;
        this.causaMapper = causaMapper;
        this.taxonomiaRepository = taxonomiaRepository;
    }

    /**
     * Save a causa.
     *
     * @param causaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CausaDTO save(CausaDTO causaDTO) {
        log.debug("Request to save Causa : {}", causaDTO);
        Causa causa = causaMapper.causaDTOToCausa(causaDTO);
        causa.setSearch(StringUtil.removerAcento(causa.getDescricao()));
        causa = causaRepository.save(causa);
        return causaMapper.causaToCausaDTO(causa);
    }

    /**
     * Get all the causas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CausaDTO> findAll(Pageable pageable, String descricao, Boolean status) {
        log.debug("Request to get all Causas");
        Page<Causa> result;

        if (status != null) {
            result = causaRepository.findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = causaRepository.findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(causa -> causaMapper.causaToCausaDTO(causa));
    }

    /**
     * Get one causa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CausaDTO findOne(Long id) {
        log.debug("Request to get Causa : {}", id);
        Causa causa = causaRepository.findOne(id);
        CausaDTO causaDTO = causaMapper.causaToCausaDTO(causa);
        return causaDTO;
    }

    /**
     * Delete the  causa by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Causa : {}", id);
        causaRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Causa com a mesma descrição
     *
     * @param causaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(CausaDTO causaDTO) {
        Causa result = causaRepository.findBySearchIgnoreCaseAndOrgaoIdIsNull(StringUtil.removerAcento(causaDTO.getDescricao()));
        return !(result == null || result.getId().equals(causaDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseOrgaoCustomRepositorio.searchByDescricao(descricao, "Causa");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<Processo> processos = processoRepository.findByCausaId(id);
        return !processos.isEmpty();
    }

    @Override
    public Boolean hasTaxonomia(Long id) {
        List<Taxonomia> result = taxonomiaRepository.findByCausaId(id);
        return !result.isEmpty();
    }
}
