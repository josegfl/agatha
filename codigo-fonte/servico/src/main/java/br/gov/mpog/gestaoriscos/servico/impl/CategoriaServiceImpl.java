package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Categoria;
import br.gov.mpog.gestaoriscos.modelo.Natureza;
import br.gov.mpog.gestaoriscos.modelo.Processo;
import br.gov.mpog.gestaoriscos.modelo.dto.CategoriaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.NaturezaDTO;
import br.gov.mpog.gestaoriscos.repositorio.CategoriaRepository;
import br.gov.mpog.gestaoriscos.repositorio.NaturezaRepository;
import br.gov.mpog.gestaoriscos.repositorio.ProcessoRepository;
import br.gov.mpog.gestaoriscos.repositorio.impl.TaxonomiaBaseCustomRepositorioImpl;
import br.gov.mpog.gestaoriscos.servico.CategoriaService;
import br.gov.mpog.gestaoriscos.servico.mapper.CategoriaMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.NaturezaMapper;
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
 * Service Implementation for managing Categoria.
 */
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService{

    private final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private NaturezaRepository naturezaRepository;

    @Autowired
    private TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private NaturezaMapper naturezaMapper;

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO){
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.categoriaDTOToCategoria(categoriaDTO);
        categoria.setSearch(StringUtil.removerAcento(categoria.getDescricao()));
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.categoriaToCategoriaDTO(categoria);
    }

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable, String descricao, Boolean status){
        log.debug("Request to get all Categorias");
        Page<Categoria> result;

        if(status != null){
            result = categoriaRepository.findBySearchContainingIgnoreCaseAndStatusOrderByDescricaoAsc(descricao, status, pageable);
        }else{
            result = categoriaRepository.findBySearchContainingIgnoreCaseOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(categoria -> categoriaMapper.categoriaToCategoriaDTO(categoria));
    }

    /**
     * Get one categoria by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findOne(Long id){
        log.debug("Request to get Categoria : {}", id);
        Categoria categoria = categoriaRepository.findOne(id);
        return categoriaMapper.categoriaToCategoriaDTO(categoria);
    }

    /**
     * Delete the  categoria by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id){
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Categoria com a mesma descrição
     *
     * @param categoriaDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(CategoriaDTO categoriaDTO){
        Categoria result = categoriaRepository.findBySearchIgnoreCase(StringUtil.removerAcento(categoriaDTO.getDescricao()));
        return !(result == null || result.getId().equals(categoriaDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao){
        return taxonomiaBaseCustomRepositorio.searchByDescricao(descricao, "Causa");
    }


    @Override
    public List<NaturezaDTO> findAllNatureza() {
        List<Natureza> result = naturezaRepository.findByStatusTrueOrderByDescricaoAsc();
        return naturezaMapper.naturezasToNaturezaDTOs(result);
    }

    @Override
    public Boolean hasProcesso(Long id){
        List<Processo> processos = processoRepository.findByCategoriaId(id);
        return !processos.isEmpty();
    }
}