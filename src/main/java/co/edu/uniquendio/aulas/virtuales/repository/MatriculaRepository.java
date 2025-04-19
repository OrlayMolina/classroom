package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.Matricula;
import co.edu.uniquendio.aulas.virtuales.model.MatriculaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, MatriculaId> {

    List<Matricula> findByCursoId(Long cursoId);

    List<Matricula> findByEstudianteId(Long estudianteId);

    @Query("SELECT m FROM Matricula m WHERE m.cursoId = :cursoId AND m.estudianteId = :estudianteId")
    Matricula findByCursoIdAndEstudianteId(@Param("cursoId") Long cursoId, @Param("estudianteId") Long estudianteId);

    @Query(value = "SELECT COUNT(*) FROM MATRICULA WHERE CURSO_ID = :cursoId", nativeQuery = true)
    Integer countEstudiantesByCursoId(@Param("cursoId") Long cursoId);
}