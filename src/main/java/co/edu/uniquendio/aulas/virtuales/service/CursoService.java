package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.CursoDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.Curso;
import co.edu.uniquendio.aulas.virtuales.repository.CursoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CursoRepository cursoRepository;
    private final ModelMapper modelMapper;

    /**
     * Crea un nuevo curso utilizando el procedimiento almacenado PK_CURSO.PCREAR_CURSO
     */
    @Transactional
    public CursoDTO crearCurso(CursoDTO cursoDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.PCREAR_CURSO")
                .registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("horario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("dia_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("curso_id", Long.class, ParameterMode.OUT);

        query.setParameter("nombre", cursoDTO.getNombre());
        query.setParameter("plan_estudio_id", cursoDTO.getPlanEstudioId());
        query.setParameter("profesor_id", cursoDTO.getProfesorId());
        query.setParameter("horario_id", cursoDTO.getHorarioId());
        query.setParameter("dia_id", cursoDTO.getDiaId());

        query.execute();

        Long cursoId = (Long) query.getOutputParameterValue("curso_id");
        cursoDTO.setCursoId(cursoId);

        return cursoDTO;
    }

    /**
     * Actualiza un curso existente utilizando el procedimiento almacenado PK_CURSO.PACTUALIZAR_CURSO
     */
    @Transactional
    public CursoDTO actualizarCurso(Long id, CursoDTO cursoDTO) {
        // Verificamos que el curso existe
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.PACTUALIZAR_CURSO")
                .registerStoredProcedureParameter("curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("horario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("dia_id", Long.class, ParameterMode.IN);

        query.setParameter("curso_id", id);
        query.setParameter("nombre", cursoDTO.getNombre());
        query.setParameter("plan_estudio_id", cursoDTO.getPlanEstudioId());
        query.setParameter("profesor_id", cursoDTO.getProfesorId());
        query.setParameter("horario_id", cursoDTO.getHorarioId());
        query.setParameter("dia_id", cursoDTO.getDiaId());

        query.execute();

        cursoDTO.setCursoId(id);
        return cursoDTO;
    }

    /**
     * Elimina un curso utilizando el procedimiento almacenado PK_CURSO.PELIMINAR_CURSO
     */
    @Transactional
    public void eliminarCurso(Long id) {
        // Verificamos que el curso existe
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.PELIMINAR_CURSO")
                .registerStoredProcedureParameter("curso_id", Long.class, ParameterMode.IN);

        query.setParameter("curso_id", id);
        query.execute();
    }

    /**
     * Obtiene los cursos por profesor utilizando la función PK_CURSO.POBTENER_CURSOS_PROFESOR
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursosProfesor(Long profesorId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.POBTENER_CURSOS_PROFESOR", "CursosMapping")
                .registerStoredProcedureParameter("p_profesor_id", Long.class, ParameterMode.IN);

        query.setParameter("p_profesor_id", profesorId);
        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        return resultList.stream().map(row -> {
            CursoDTO dto = new CursoDTO();
            dto.setCursoId((Long) row[0]);
            dto.setNombre((String) row[1]);
            dto.setPlanEstudioId((Long) row[2]);
            dto.setProfesorId((Long) row[3]);
            dto.setHorarioId((Long) row[4]);
            dto.setDiaId((Long) row[5]);
            dto.setHoraInicio((String) row[6]);
            dto.setHoraFin((String) row[7]);
            dto.setUbicacion((String) row[8]);
            dto.setNombreDia((String) row[9]);
            dto.setNombrePlanEstudio((String) row[10]);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursosEstudiante(Long estudianteId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.POBTENER_CURSOS_ESTUDIANTE", "CursosEstudianteMapping")
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_estudiante_id", estudianteId);
        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        return resultList.stream().map(row -> {
            CursoDTO dto = new CursoDTO();
            dto.setCursoId((Long) row[0]);
            dto.setNombre((String) row[1]);
            dto.setPlanEstudioId((Long) row[2]);
            dto.setProfesorId((Long) row[3]);
            dto.setHorarioId((Long) row[4]);
            dto.setDiaId((Long) row[5]);
            dto.setHoraInicio((String) row[6]);
            dto.setHoraFin((String) row[7]);
            dto.setUbicacion((String) row[8]);
            dto.setNombreDia((String) row[9]);
            dto.setNombrePlanEstudio((String) row[10]);
            dto.setNombreProfesor((String) row[11]);
            dto.setApellidoProfesor((String) row[12]);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Matricula un estudiante en un curso utilizando el procedimiento PK_CURSO.PMATRICULAR_ESTUDIANTE
     */
    @Transactional
    public void matricularEstudiante(Long cursoId, Long estudianteId) {
        // Verificamos que el curso existe
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + cursoId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.PMATRICULAR_ESTUDIANTE")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();
    }

    /**
     * Retira un estudiante de un curso utilizando el procedimiento PK_CURSO.PRETIRAR_ESTUDIANTE
     */
    @Transactional
    public void retirarEstudiante(Long cursoId, Long estudianteId) {
        // Verificamos que el curso existe
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + cursoId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.PRETIRAR_ESTUDIANTE")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();
    }

    /**
     * Verifica si un estudiante está matriculado en un curso utilizando la función PK_CURSO.PESTA_MATRICULADO
     */
    @Transactional(readOnly = true)
    public boolean estaMatriculado(Long cursoId, Long estudianteId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_CURSO.PESTA_MATRICULADO")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("RETURN_VALUE", Boolean.class, ParameterMode.OUT);

        query.setParameter("p_curso_id", cursoId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();

        return (Boolean) query.getOutputParameterValue("RETURN_VALUE");
    }

    /**
     * Obtiene un curso por su ID
     */
    @Transactional(readOnly = true)
    public CursoDTO getCursoPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));

        return modelMapper.map(curso, CursoDTO.class);
    }

    /**
     * Obtiene todos los cursos
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> listarTodosLosCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(curso -> modelMapper.map(curso, CursoDTO.class))
                .collect(Collectors.toList());
    }
}
