package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.ControleEvento;
import br.gov.mpog.gestaoriscos.modelo.Operacao;
import br.gov.mpog.gestaoriscos.modelo.dto.OperacaoDTO;
import br.gov.mpog.gestaoriscos.repositorio.ControleEventoRepository;
import br.gov.mpog.gestaoriscos.repositorio.OperacaoRepository;
import br.gov.mpog.gestaoriscos.repositorio.impl.TaxonomiaBaseCustomRepositorioImpl;
import br.gov.mpog.gestaoriscos.servico.OperacaoService;
import br.gov.mpog.gestaoriscos.servico.mapper.OperacaoMapper;
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
 * Service Implementation for managing Operacao.
 */
@Service
@Transactional
public class OperacaoServiceImpl implements OperacaoService {

    private final Logger log = LoggerFactory.getLogger(OperacaoServiceImpl.class);

    private final OperacaoRepository operacaoRepository;

    private final TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio;

    private final OperacaoMapper operacaoMapper;

    private final ControleEventoRepository controleEventoRepository;

    @Autowired
    public OperacaoServiceImpl(OperacaoRepository operacaoRepository, TaxonomiaBaseCustomRepositorioImpl taxonomiaBaseCustomRepositorio, OperacaoMapper operacaoMapper, ControleEventoRepository controleEventoRepository) {
        this.operacaoRepository = operacaoRepository;
        this.taxonomiaBaseCustomRepositorio = taxonomiaBaseCustomRepositorio;
        this.operacaoMapper = operacaoMapper;
        this.controleEventoRepository = controleEventoRepository;
    }

    /**
     * Save a operacao.
     *
     * @param operacaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OperacaoDTO save(OperacaoDTO operacaoDTO) {
        log.debug("Request to save Operacao : {}", operacaoDTO);
        Operacao operacao = operacaoMapper.operacaoDTOToOperacao(operacaoDTO);
        operacao.setSearch(StringUtil.removerAcento(operacao.getDescricao()));
        operacao = operacaoRepository.save(operacao);
        return operacaoMapper.operacaoToOperacaoDTO(operacao);
    }

    /**
     * Get all the operacaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperacaoDTO> findAll(Pageable pageable, String descricao, Boolean status) {
        log.debug("Request to get all Operacaos");
        Page<Operacao> result;

        if (status != null) {
            result = operacaoRepository.findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(descricao, status, pageable);
        } else {
            result = operacaoRepository.findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(descricao, pageable);
        }
        return result.map(operacao -> operacaoMapper.operacaoToOperacaoDTO(operacao));
    }

    /**
     * Get one operacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OperacaoDTO findOne(Long id) {
        log.debug("Request to get Operacao : {}", id);
        Operacao operacao = operacaoRepository.findOne(id);
        OperacaoDTO operacaoDTO = operacaoMapper.operacaoToOperacaoDTO(operacao);
        return operacaoDTO;
    }

    /**
     * Delete the  operacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operacao : {}", id);
        operacaoRepository.delete(id);
    }

    /**
     * Verifica se existe alguma Operacao com a mesma descrição
     *
     * @param operacaoDTO a entidade para verificar
     * @return true se exisiter ou false senão existir
     */
    @Override
    public Boolean verificarExistencia(OperacaoDTO operacaoDTO) {
        Operacao result = operacaoRepository.findBySearchIgnoreCase(StringUtil.removerAcento(operacaoDTO.getDescricao()));
        return !(result == null || result.getId().equals(operacaoDTO.getId()));
    }

    @Override
    public List<String> searchByDescricao(String descricao) {
        return taxonomiaBaseCustomRepositorio.searchByDescricao(descricao, "Operacao");
    }

    @Override
    public Boolean hasProcesso(Long id) {
        List<ControleEvento> result = controleEventoRepository.findByOperacaoId(id);
        return !result.isEmpty();
    }

}
