package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByProfesorUsuarioId(Long profesorId);

    @Query("SELECT c FROM Curso c JOIN c.estudiantes e WHERE e.usuarioId = :estudianteId")
    List<Curso> findByEstudianteId(@Param("estudianteId") Long estudianteId);

    List<Curso> findByPlanEstudioPlanEstudioId(Long planEstudioId);

    List<Curso> findByDiaDiaId(Long diaId);

    List<Curso> findByHorarioHorarioId(Long horarioId);
}
