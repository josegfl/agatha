package br.gov.mpog.gestaoriscos.repositorio;

import br.gov.mpog.gestaoriscos.modelo.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Processo entity.
 */
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    @Query(value = "SELECT pro FROM Processo pro INNER JOIN pro.identificacao iden INNER JOIN iden.eventos er INNER JOIN er.evento evt WHERE evt.id = :eventId ")
    List<Processo> findByEventoId(@Param("eventId") Long eventId);

    @Query(value = "SELECT pro FROM Processo pro INNER JOIN pro.identificacao iden INNER JOIN iden.eventos er INNER JOIN er.causas ec INNER JOIN ec.causa cau WHERE cau.id = :causaId ")
    List<Processo> findByCausaId(@Param("causaId") Long causaId);

    @Query(value = "SELECT pro FROM Processo pro INNER JOIN pro.identificacao iden INNER JOIN iden.eventos er INNER JOIN er.consequencias ec INNER JOIN ec.consequencia cons WHERE cons.id = :consequenciaId ")
    List<Processo> findByConsequenciaId(@Param("consequenciaId") Long consequenciaId);

    @Query(value = "SELECT pro FROM Processo pro INNER JOIN pro.identificacao iden INNER JOIN iden.eventos er INNER JOIN er.categoria cat WHERE cat.id = :categoriaId ")
    List<Processo> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query(value = "SELECT pro FROM Processo pro INNER JOIN pro.identificacao iden INNER JOIN iden.eventos er INNER JOIN er.controleEventos crtEvt INNER JOIN crtEvt.controle crt WHERE crt.id = :controleId ")
    List<Processo> findByControleId(@Param("controleId") Long controleId);

    List<Processo> findByMacroprocessoId(Long id);
}