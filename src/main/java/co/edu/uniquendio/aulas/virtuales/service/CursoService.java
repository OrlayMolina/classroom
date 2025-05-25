package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.CursoDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.Curso;
import co.edu.uniquendio.aulas.virtuales.repository.CursoRepository;
import jakarta.persistence.*;
import jakarta.persistence.Query;
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
     * Crea un nuevo curso utilizando el procedimiento almacenado PCREAR_CURSO
     */
    @Transactional
    public CursoDTO crearCurso(CursoDTO cursoDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PCREAR_CURSO")
                .registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_horario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_dia_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_nombre", cursoDTO.getNombre());
        query.setParameter("p_plan_estudio_id", cursoDTO.getPlanEstudioId());
        query.setParameter("p_profesor_id", cursoDTO.getProfesorId());
        query.setParameter("p_horario_id", cursoDTO.getHorarioId());
        query.setParameter("p_dia_id", cursoDTO.getDiaId() != null ? cursoDTO.getDiaId() : 1L);

        query.execute();

        Long cursoId = (Long) query.getOutputParameterValue("p_curso_id");
        cursoDTO.setCursoId(cursoId);

        return cursoDTO;
    }

    /**
     * Actualiza un curso existente utilizando el procedimiento almacenado PACTUALIZAR_CURSO
     */
    @Transactional
    public CursoDTO actualizarCurso(Long id, CursoDTO cursoDTO) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PACTUALIZAR_CURSO")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_horario_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_dia_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", id);
        query.setParameter("p_nombre", cursoDTO.getNombre());
        query.setParameter("p_plan_estudio_id", cursoDTO.getPlanEstudioId());
        query.setParameter("p_profesor_id", cursoDTO.getProfesorId());
        query.setParameter("p_horario_id", cursoDTO.getHorarioId());
        query.setParameter("p_dia_id", cursoDTO.getDiaId() != null ? cursoDTO.getDiaId() : 1L);

        query.execute();

        cursoDTO.setCursoId(id);
        return cursoDTO;
    }

    /**
     * Elimina un curso utilizando el procedimiento almacenado PELIMINAR_CURSO
     */
    @Transactional
    public void eliminarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PELIMINAR_CURSO")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", id);
        query.execute();
    }

    /**
     * Obtiene los cursos por profesor utilizando consulta nativa - VERSIÓN CORREGIDA
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursosProfesor(Long profesorId) {
        String sql = "SELECT " +
                "   c.CURSO_ID, " +
                "   c.NOMBRE, " +
                "   c.PLAN_ESTUDIO_ID, " +
                "   c.PROFESOR_ID, " +
                "   c.HORARIO_ID, " +
                "   h.DIA_ID, " +
                "   h.HORA_INICIO, " +
                "   h.HORA_FIN, " +
                "   h.UBICACION, " +
                "   d.NOMBRE_DIA, " +
                "   pe.NOMBRE AS NOMBRE_PLAN_ESTUDIO " +
                "FROM " +
                "   CURSO c " +
                "JOIN HORARIO h ON c.HORARIO_ID = h.HORARIO_ID " +
                "JOIN DIAS_SEMANA d ON h.DIA_ID = d.DIA_ID " +
                "JOIN PLAN_ESTUDIO pe ON c.PLAN_ESTUDIO_ID = pe.PLAN_ESTUDIO_ID " +
                "WHERE c.PROFESOR_ID = ?1";

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(sql)
                .setParameter(1, profesorId)
                .getResultList();

        return resultList.stream().map(row -> {
            CursoDTO dto = new CursoDTO();
            dto.setCursoId(((Number) row[0]).longValue());
            dto.setNombre((String) row[1]);
            dto.setPlanEstudioId(((Number) row[2]).longValue());
            dto.setProfesorId(((Number) row[3]).longValue());
            dto.setHorarioId(((Number) row[4]).longValue());
            dto.setDiaId(((Number) row[5]).longValue());
            dto.setHoraInicio((String) row[6]);
            dto.setHoraFin((String) row[7]);
            dto.setUbicacion((String) row[8]);
            dto.setNombreDia((String) row[9]);
            dto.setNombrePlanEstudio((String) row[10]);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Obtiene cursos de estudiante utilizando consulta nativa
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursosEstudiante(Long estudianteId) {
        String sql = "SELECT " +
                "   c.CURSO_ID, " +
                "   c.NOMBRE, " +
                "   c.PLAN_ESTUDIO_ID, " +
                "   c.PROFESOR_ID, " +
                "   c.HORARIO_ID, " +
                "   h.DIA_ID, " +
                "   h.HORA_INICIO, " +
                "   h.HORA_FIN, " +
                "   h.UBICACION, " +
                "   d.NOMBRE_DIA, " +
                "   pe.NOMBRE AS NOMBRE_PLAN_ESTUDIO, " +
                "   u.NOMBRE AS NOMBRE_PROFESOR, " +
                "   u.APELLIDO AS APELLIDO_PROFESOR " +
                "FROM CURSO c " +
                "JOIN MATRICULA m ON c.CURSO_ID = m.CURSO_ID " +
                "JOIN HORARIO h ON c.HORARIO_ID = h.HORARIO_ID " +
                "JOIN DIAS_SEMANA d ON h.DIA_ID = d.DIA_ID " +
                "JOIN PLAN_ESTUDIO pe ON c.PLAN_ESTUDIO_ID = pe.PLAN_ESTUDIO_ID " +
                "JOIN USUARIO u ON c.PROFESOR_ID = u.USUARIO_ID " +
                "WHERE m.ESTUDIANTE_ID = :estudianteId";

        Query query = entityManager.createNativeQuery(sql);
        Parameter<Integer> param = query.getParameter(1, Integer.class);
        query.setParameter(param, estudianteId.intValue());

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        return resultList.stream().map(row -> {
            CursoDTO dto = new CursoDTO();
            dto.setCursoId(((Number) row[0]).longValue());
            dto.setNombre((String) row[1]);
            dto.setPlanEstudioId(((Number) row[2]).longValue());
            dto.setProfesorId(((Number) row[3]).longValue());
            dto.setHorarioId(((Number) row[4]).longValue());
            dto.setDiaId(((Number) row[5]).longValue());
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
     * Matricula un estudiante en un curso utilizando el procedimiento PMATRICULAR_ESTUDIANTE
     */
    @Transactional
    public void matricularEstudiante(Long cursoId, Long estudianteId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + cursoId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PMATRICULAR_ESTUDIANTE")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();
    }

    /**
     * Retira un estudiante de un curso utilizando el procedimiento PRETIRAR_ESTUDIANTE
     */
    @Transactional
    public void retirarEstudiante(Long cursoId, Long estudianteId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + cursoId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PRETIRAR_ESTUDIANTE")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();
    }

    /**
     * Verifica si un estudiante está matriculado en un curso utilizando la función PESTA_MATRICULADO
     */
    @Transactional(readOnly = true)
    public boolean estaMatriculado(Long cursoId, Long estudianteId) {
        String sql = "SELECT PESTA_MATRICULADO(?1, ?2) FROM DUAL";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, cursoId.intValue());
        query.setParameter(2, estudianteId.intValue());

        Number result = (Number) query.getSingleResult();
        return result.intValue() == 1;
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
