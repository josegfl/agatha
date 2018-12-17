package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Glossario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Glossario entity.
 */
public interface GlossarioRepository extends JpaRepository<Glossario, Long>{

    Glossario findByTermoSearchIgnoreCase(String termo);

    Page<Glossario> findByTermoSearchContainingIgnoreCaseAndDescricaoSearchContainingIgnoreCaseOrderByTermoAsc(String termo, String descricao, Pageable pageable);

    Page<Glossario> findByTermoSearchContainingIgnoreCaseAndDescricaoSearchContainingIgnoreCaseAndStatusOrderByTermoAsc(String termo, String descricao, Boolean status, Pageable pageable);

    List<Glossario> findByStatusTrue();
}