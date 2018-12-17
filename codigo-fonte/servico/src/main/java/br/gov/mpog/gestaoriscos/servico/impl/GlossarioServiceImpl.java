package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Glossario;
import br.gov.mpog.gestaoriscos.modelo.dto.GlossarioDTO;
import br.gov.mpog.gestaoriscos.repositorio.GlossarioCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.GlossarioRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.servico.GlossarioService;
import br.gov.mpog.gestaoriscos.servico.mapper.GlossarioMapper;
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
 * Service Implementation for managing Glossario.
 */
@Service
@Transactional
public class GlossarioServiceImpl implements GlossarioService{

    private final Logger log = LoggerFactory.getLogger(GlossarioServiceImpl.class);

    @Autowired
    private GlossarioRepository glossarioRepository;

    @Autowired
    private GlossarioCustomRepositorio glossarioCustomRepositorio;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private GlossarioMapper glossarioMapper;

    /**
     * Save a glossario.
     *
     * @param glossarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GlossarioDTO save(GlossarioDTO glossarioDTO){
        log.debug("Request to save Glossario : {}", glossarioDTO);
        Glossario glossario = glossarioMapper.glossarioDTOToGlossario(glossarioDTO);
        glossario.setTermoSearch(StringUtil.removerAcento(glossario.getTermo()));
        glossario.setDescricaoSearch(StringUtil.removerAcento(glossario.getDescricao()));
        glossario = glossarioRepository.save(glossario);
        return glossarioMapper.glossarioToGlossarioDTO(glossario);
    }

    /**
     * Get all the glossarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GlossarioDTO> findAll(Pageable pageable, String termo, String descricao, Boolean status){
        log.debug("Request to get all Glossarios");
        Page<Glossario> result;

        if(status != null){
            result = glossarioRepository.findByTermoSearchContainingIgnoreCaseAndDescricaoSearchContainingIgnoreCaseAndStatusOrderByTermoAsc(termo, descricao, status, pageable);
        }else{
            result = glossarioRepository.findByTermoSearchContainingIgnoreCaseAndDescricaoSearchContainingIgnoreCaseOrderByTermoAsc(termo, descricao, pageable);
        }
        return result.map(glossario -> glossarioMapper.glossarioToGlossarioDTO(glossario));
    }

    /**
     * Get one glossario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GlossarioDTO findOne(Long id){
        log.debug("Request to get Glossario : {}", id);
        Glossario glossario = glossarioRepository.findOne(id);
        return glossarioMapper.glossarioToGlossarioDTO(glossario);
    }

    /**
     * Delete the  glossario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id){
        log.debug("Request to delete Glossario : {}", id);
        glossarioRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Glossario com a mesma descrição
     *
     * @param glossarioDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(GlossarioDTO glossarioDTO){
        Glossario result = glossarioRepository.findByTermoSearchIgnoreCase(StringUtil.removerAcento(glossarioDTO.getTermo()));
        return !(result == null || result.getId().equals(glossarioDTO.getId()));
    }

    @Override
    public List<String> searchByTermo(String descricao){
        return glossarioCustomRepositorio.searchByTermo(descricao);
    }

    @Override
    public List<String> searchByDescricao(String descricao){
        return glossarioCustomRepositorio.searchByDescricao(descricao);
    }

    @Override
    public List<GlossarioDTO> findAll(){
        List<Glossario> result = glossarioRepository.findByStatusTrue();
        return glossarioMapper.glossariosToGlossarioDTOs(result);
    }
}