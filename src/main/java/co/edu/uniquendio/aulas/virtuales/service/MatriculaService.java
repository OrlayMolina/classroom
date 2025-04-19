package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.MatriculaDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.Curso;
import co.edu.uniquendio.aulas.virtuales.model.Matricula;
import co.edu.uniquendio.aulas.virtuales.model.MatriculaId;
import co.edu.uniquendio.aulas.virtuales.model.Usuario;
import co.edu.uniquendio.aulas.virtuales.repository.CursoRepository;
import co.edu.uniquendio.aulas.virtuales.repository.MatriculaRepository;
import co.edu.uniquendio.aulas.virtuales.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    @PersistenceContext
    private EntityManager entityManager;

    private final MatriculaRepository matriculaRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    /**
     * Matricula un estudiante en un curso utilizando el procedimiento almacenado PK_USUARIO.PMATRICULAR_ESTUDIANTE
     */
    @Transactional
    public MatriculaDTO matricularEstudiante(MatriculaDTO matriculaDTO) {
        // Verificar que el curso existe
        if (!cursoRepository.existsById(matriculaDTO.getCursoId())) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + matriculaDTO.getCursoId());
        }

        // Verificar que el estudiante existe
        if (!usuarioRepository.existsById(matriculaDTO.getEstudianteId())) {
            throw new ResourceNotFoundException("Estudiante no encontrado con id: " + matriculaDTO.getEstudianteId());
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PMATRICULAR_ESTUDIANTE")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", matriculaDTO.getCursoId());
        query.setParameter("p_estudiante_id", matriculaDTO.getEstudianteId());

        query.execute();

        // La matriculación fue exitosa, devolver el DTO con información adicional
        Curso curso = cursoRepository.findById(matriculaDTO.getCursoId()).orElseThrow();
        Usuario estudiante = usuarioRepository.findById(matriculaDTO.getEstudianteId()).orElseThrow();

        matriculaDTO.setFechaMatricula(new Date());
        matriculaDTO.setNombreCurso(curso.getNombre());
        matriculaDTO.setNombreEstudiante(estudiante.getNombre());
        matriculaDTO.setApellidoEstudiante(estudiante.getApellido());

        return matriculaDTO;
    }

    /**
     * Desmatricula un estudiante de un curso utilizando el procedimiento almacenado PK_USUARIO.PDESMATRICULAR_ESTUDIANTE
     */
    @Transactional
    public void desmatricularEstudiante(Long cursoId, Long estudianteId) {
        // Verificar que la matrícula existe
        MatriculaId id = new MatriculaId(cursoId, estudianteId);
        if (!matriculaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Matrícula no encontrada para curso id: " + cursoId + " y estudiante id: " + estudianteId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_USUARIO.PDESMATRICULAR_ESTUDIANTE")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();
    }

    /**
     * Obtiene todos los estudiantes matriculados en un curso
     */
    @Transactional(readOnly = true)
    public List<MatriculaDTO> obtenerEstudiantesPorCurso(Long cursoId) {
        List<Matricula> matriculas = matriculaRepository.findByCursoId(cursoId);

        return matriculas.stream()
                .map(matricula -> {
                    MatriculaDTO dto = modelMapper.map(matricula, MatriculaDTO.class);
                    dto.setNombreCurso(matricula.getCurso().getNombre());
                    dto.setNombreEstudiante(matricula.getEstudiante().getNombre());
                    dto.setApellidoEstudiante(matricula.getEstudiante().getApellido());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los cursos en los que está matriculado un estudiante
     */
    @Transactional(readOnly = true)
    public List<MatriculaDTO> obtenerCursosPorEstudiante(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);

        return matriculas.stream()
                .map(matricula -> {
                    MatriculaDTO dto = modelMapper.map(matricula, MatriculaDTO.class);
                    dto.setNombreCurso(matricula.getCurso().getNombre());
                    dto.setNombreEstudiante(matricula.getEstudiante().getNombre());
                    dto.setApellidoEstudiante(matricula.getEstudiante().getApellido());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un estudiante está matriculado en un curso
     */
    @Transactional(readOnly = true)
    public boolean verificarMatricula(Long cursoId, Long estudianteId) {
        MatriculaId id = new MatriculaId(cursoId, estudianteId);
        return matriculaRepository.existsById(id);
    }

    /**
     * Obtiene el conteo de estudiantes matriculados en un curso
     */
    @Transactional(readOnly = true)
    public Integer contarEstudiantesPorCurso(Long cursoId) {
        return matriculaRepository.countEstudiantesByCursoId(cursoId);
    }
}