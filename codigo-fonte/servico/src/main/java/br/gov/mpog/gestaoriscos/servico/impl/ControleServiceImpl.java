package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Controle;
import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.PlanoControle;
import br.gov.mpog.gestaoriscos.modelo.Taxonomia;
import br.gov.mpog.gestaoriscos.modelo.dto.ControleDTO;
import br.gov.mpog.gestaoriscos.repositorio.ControleEventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.ControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.PlanoControleRepository;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaBaseOrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.TaxonomiaRepository;
import br.gov.mpog.gestaoriscos.servico.ControleService;
import br.gov.mpog.gestaoriscos.servico.mapper.ControleMapper;
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
 * Service Implementation for managing Controle.
 */
@Service
@Transactional
public class ControleServiceImpl implements ControleService {

    private final Logger log = LoggerFactory.getLogger(ControleServiceImpl.class);

    private final ControleRepository controleRepository;

    private final TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio;

    private final ControleMapper controleMapper;

    private final ControleEventoRepository controleEventoRepository;

    private final PlanoControleRepository planoControleRepository;

    private final TaxonomiaRepository taxonomiaRepository;

    @Autowired
    public ControleServiceImpl(ControleRepository controleRepository, TaxonomiaBaseOrgaoCustomRepositorio taxonomiaBaseOrgaoCustomRepositorio, ControleMapper controleMapper, ControleEventoRepository controleEventoRepository, PlanoControleRepository planoControleRepository, TaxonomiaRepository taxonomiaRepository) {
        this.controleRepository = controleRepository;
        this.taxonomiaBaseOrgaoCustomRepositorio = taxonomiaBaseOrgaoCustomRepositorio;
        this.controleMapper = controleMapper;
        this.controleEventoRepository = controleEventoRepository;
        this.planoControleRepository = planoControleRepository;
        this.taxonomiaRepository = taxonomiaRepository;
    }

    /**
     * Save a controle.
     *
     * @param controleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ControleDTO save(ControleDTO controleDTO) {
        log.debug("Request to save Controle : {}", controleDTO);
        Controle controle = controleMapper.controleDTOToControle(controleDTO);
        controle.setSearch(StringUtil.removerAcento(controle.getDescricao()));
        controle = controleRepository.save(controle);
        return controleMapper.controleToControleDTO(controle);
    }

    /**
     * Get all the controles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ControleDTO> findAll(Pageable pageable, String descricao, Boolean status) {
        log.debug("Request to get all Controles");
        Page<Controle> result;

        if (status != null) {
            result = controleRepository.findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = controleRepository.findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(controle -> controleMapper.controleToControleDTO(controle));
    }

    /**
     * Get one controle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ControleDTO findOne(Long id) {
        log.debug("Request to get Controle : {}", id);
        Controle controle = controleRepository.findOne(id);
        ControleDTO controleDTO = controleMapper.controleToControleDTO(controle);
        return controleDTO;
    }

    /**
     * Delete the  controle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Controle : {}", id);
        controleRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Controle com a mesma descrição
     *
     * @param controleDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(ControleDTO controleDTO) {
        Controle result = controleRepository.findBySearchIgnoreCaseAndOrgaoIdIsNull(StringUtil.removerAcento(controleDTO.getDescricao()));
        return !(result == null || result.getId().equals(controleDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseOrgaoCustomRepositorio.searchByDescricao(descricao, "Controle");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<ControleEvento> controleEventos = controleEventoRepository.findByControleId(id);
        List<PlanoControle> planoControles = planoControleRepository.findByControleId(id);
        return !controleEventos.isEmpty() || !planoControles.isEmpty();
    }

    @Override
    public Boolean hasTaxonomia(Long id) {
        List<Taxonomia> result = taxonomiaRepository.findByControleId(id);
        return !result.isEmpty();
    }
}
