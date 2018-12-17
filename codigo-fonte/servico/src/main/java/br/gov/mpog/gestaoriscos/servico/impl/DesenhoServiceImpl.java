package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.Desenho;
import br.gov.mpog.gestaoriscos.modelo.dto.DesenhoDTO;
import br.gov.mpog.gestaoriscos.repositorio.ControleEventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.DesenhoRepository;
import br.gov.mpog.gestaoriscos.repositorio.impl.TaxonomiaBaseCustomRepositorioImpl;
import br.gov.mpog.gestaoriscos.servico.DesenhoService;
import br.gov.mpog.gestaoriscos.servico.mapper.DesenhoMapper;
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
 * Service Implementation for managing Desenho.
 */
@Service
@Transactional
public class DesenhoServiceImpl implements DesenhoService {

    private final Logger log = LoggerFactory.getLogger(DesenhoServiceImpl.class);

    private final DesenhoRepository desenhoRepository;

    private final TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio;

    private final DesenhoMapper desenhoMapper;

    private final ControleEventoRepository controleEventoRepository;

    @Autowired
    public DesenhoServiceImpl(DesenhoRepository desenhoRepository, TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio, DesenhoMapper desenhoMapper, ControleEventoRepository controleEventoRepository) {
        this.desenhoRepository = desenhoRepository;
        this.taxonomiaBaseCustomRepositorio = taxonomiaBaseCustomRepositorio;
        this.desenhoMapper = desenhoMapper;
        this.controleEventoRepository = controleEventoRepository;
    }

    /**
     * Save a desenho.
     *
     * @param desenhoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesenhoDTO save(DesenhoDTO desenhoDTO) {
        log.debug("Request to save Desenho : {}", desenhoDTO);
        Desenho desenho = desenhoMapper.desenhoDTOToDesenho(desenhoDTO);
        desenho.setSearch(StringUtil.removerAcento(desenho.getDescricao()));
        desenho = desenhoRepository.save(desenho);
        return desenhoMapper.desenhoToDesenhoDTO(desenho);
    }

    /**
     * Get all the desenhos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesenhoDTO> findAll(Pageable pageable, String descricao, Boolean status) {
        log.debug("Request to get all Desenhos");
        Page<Desenho> result;

        if (status != null) {
            result = desenhoRepository.findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = desenhoRepository.findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(desenho -> desenhoMapper.desenhoToDesenhoDTO(desenho));
    }

    /**
     * Get one desenho by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DesenhoDTO findOne(Long id) {
        log.debug("Request to get Desenho : {}", id);
        Desenho desenho = desenhoRepository.findOne(id);
        DesenhoDTO desenhoDTO = desenhoMapper.desenhoToDesenhoDTO(desenho);
        return desenhoDTO;
    }

    /**
     * Delete the  desenho by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Desenho : {}", id);
        desenhoRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Desenho com a mesma descrição
     *
     * @param desenhoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(DesenhoDTO desenhoDTO) {
        Desenho result = desenhoRepository.findBySearchIgnoreCase(StringUtil.removerAcento(desenhoDTO.getDescricao()));
        return !(result == null || result.getId().equals(desenhoDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseCustomRepositorio.searchByDescricao(descricao, "Desenho");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<ControleEvento> result = controleEventoRepository.findByDesenhoId(id);
        return !result.isEmpty();
    }
}
