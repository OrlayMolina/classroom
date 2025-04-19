package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.PlanEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanEstudioRepository extends JpaRepository<PlanEstudio, Long> {

    List<PlanEstudio> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT pe FROM PlanEstudio pe JOIN pe.contenidos c WHERE c.contenidoId = :contenidoId")
    List<PlanEstudio> findByContenidoId(@Param("contenidoId") Long contenidoId);

    @Query("SELECT pe FROM PlanEstudio pe JOIN pe.contenidos c JOIN c.unidad u WHERE u.unidadId = :unidadId")
    List<PlanEstudio> findByUnidadId(@Param("unidadId") Long unidadId);
}