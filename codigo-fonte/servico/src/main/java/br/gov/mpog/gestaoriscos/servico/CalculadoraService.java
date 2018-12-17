package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.CalculadoraDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.TipoCalculadoraDTO;

import java.util.List;

/**
 * Service Interface for managing Calculadora.
 */
public interface CalculadoraService{

    /**
     * Save a calculadora.
     *
     * @param calculadoraDTO the entity to save
     * @return the persisted entity
     */
    CalculadoraDTO save(CalculadoraDTO calculadoraDTO);

    /**
     * Get all the calculadoras.
     *
     * @return the list of entities
     */
    List<CalculadoraDTO> findAll();

    List<TipoCalculadoraDTO> findAllTiposCalculadora();
}
