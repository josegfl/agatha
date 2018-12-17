package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Calculadora;
import br.gov.mpog.gestaoriscos.modelo.ImpactoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.ProbabilidadeCalculadora;
import br.gov.mpog.gestaoriscos.modelo.TipoCalculadora;
import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoCalculadoraDTO;
import br.gov.mpog.gestaoriscos.repositorio.CalculadoraRepository;
import br.gov.mpog.gestaoriscos.repositorio.TipoCalculadoraRepository;
import br.gov.mpog.gestaoriscos.servico.CalculadoraService;
import br.gov.mpog.gestaoriscos.servico.mapper.CalculadoraMapper;
import br.gov.mpog.gestaoriscos.servico.mapper.TipoCalculadoraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Calculadora.
 */
@Service
@Transactional
public class CalculadoraServiceImpl implements CalculadoraService{

    private final Logger log = LoggerFactory.getLogger(CalculadoraServiceImpl.class);

    @Autowired
    private CalculadoraRepository calculadoraRepository;

    @Autowired
    private TipoCalculadoraRepository tipoCalculadoraRepository;

    @Autowired
    private CalculadoraMapper calculadoraMapper;

    @Autowired
    private TipoCalculadoraMapper tipoCalculadoraMapper;

    /**
     * Save a calculadora.
     *
     * @param calculadoraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CalculadoraDTO save(CalculadoraDTO calculadoraDTO){
        log.debug("Request to save Calculadora : {}", calculadoraDTO);
        Calculadora calculadora = calculadoraMapper.calculadoraDTOToCalculadora(calculadoraDTO);

        for(ImpactoCalculadora impactoCalculadora : calculadora.getImpactos()){
            impactoCalculadora.setCalculadora(calculadora);
        }

        for(ProbabilidadeCalculadora probabilidadeCalculadora : calculadora.getProbabilidades()){
            probabilidadeCalculadora.setCalculadora(calculadora);
        }

        calculadora = calculadoraRepository.save(calculadora);
        return calculadoraMapper.calculadoraToCalculadoraDTO(calculadora);
    }

    /**
     * Get all the calculadoras.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CalculadoraDTO> findAll(){
        log.debug("Request to get all Calculadoras");
        List<Calculadora> result = calculadoraRepository.findByProcessoIsNull();
        return calculadoraMapper.calculadorasToCalculadoraDTOs(result);
    }

    @Override
    public List<TipoCalculadoraDTO> findAllTiposCalculadora(){
        List<TipoCalculadora> result = tipoCalculadoraRepository.findAll();
        return tipoCalculadoraMapper.tipoCalculadorasToTipoCalculadoraDTOs(result);
    }
}
