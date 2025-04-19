package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    List<Pregunta> findByProfesorUsuarioId(Long profesorId);

    List<Pregunta> findByPreguntaPadreIsNull();

    List<Pregunta> findByPreguntaPadrePreguntaId(Long preguntaPadreId);

    @Query("SELECT COUNT(p) > 0 FROM Pregunta p WHERE p.profesor.usuarioId = :profesorId AND p.preguntaPadre.preguntaId = :preguntaPadreId")
    boolean existsByProfesorIdAndPreguntaPadreId(@Param("profesorId") Long profesorId, @Param("preguntaPadreId") Long preguntaPadreId);
}