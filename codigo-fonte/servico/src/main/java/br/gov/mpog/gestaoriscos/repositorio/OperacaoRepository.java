package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Operacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Operacao entity.
 */
public interface OperacaoRepository extends JpaRepository<Operacao, Long>{

    Operacao findBySearchIgnoreCase(String descricao);

    Page<Operacao> findBySearchContainingIgnoreCaseAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Pageable pageable);

    Page<Operacao> findBySearchContainingIgnoreCaseAndStatusAndOrgaoIsNullOrderByDescricaoAsc(String descricao, Boolean status, Pageable pageable);

    List<Operacao> findByStatusTrueOrderByDescricaoAsc();
}