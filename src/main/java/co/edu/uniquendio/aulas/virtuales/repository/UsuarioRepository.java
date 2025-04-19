package co.edu.uniquendio.aulas.virtuales.repository;

import co.edu.uniquendio.aulas.virtuales.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolRolId(Long rolId);

    @Query("SELECT u FROM Usuario u JOIN u.rol r WHERE r.nombreRol = :nombreRol")
    List<Usuario> findByNombreRol(@Param("nombreRol") String nombreRol);

    @Query("SELECT u FROM Usuario u JOIN u.cursosEstudiante c WHERE c.cursoId = :cursoId")
    List<Usuario> findEstudiantesByCursoId(@Param("cursoId") Long cursoId);

    @Query("SELECT u FROM Usuario u WHERE u.rol.rolId = 1 AND u.usuarioId IN (SELECT c.profesor.usuarioId FROM Curso c)")
    List<Usuario> findProfesoresConCursos();

    @Query("SELECT u FROM Usuario u WHERE u.rol.rolId = 2 AND u.usuarioId IN (SELECT e.estudiante.usuarioId FROM EnvioExamen e WHERE e.examen.examenId = :examenId)")
    List<Usuario> findEstudiantesQueRealizaronExamen(@Param("examenId") Long examenId);
}
