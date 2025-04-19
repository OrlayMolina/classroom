package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    List<Examen> findByProfesorUsuarioId(Long profesorId);

    List<Examen> findByCursoCursoId(Long cursoId);

    List<Examen> findByTemaTemaId(Long temaId);

    List<Examen> findByTipoPruebaTipoPruebaId(Long tipoPruebaId);
}