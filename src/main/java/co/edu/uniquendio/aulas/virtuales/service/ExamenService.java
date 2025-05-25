package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.ConfiguracionExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.EnvioExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.ExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.PreguntaDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.Examen;
import co.edu.uniquendio.aulas.virtuales.repository.ExamenRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ExamenRepository examenRepository;
    private final ModelMapper modelMapper;

    /**
     * Crea un nuevo examen utilizando el procedimiento almacenado PCREAR_EXAMEN_COMPLETO
     */
    @Transactional
    public ExamenDTO crearExamen(ExamenDTO examenDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PCREAR_EXAMEN_COMPLETO")
                .registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_descripcion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_categoria", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cant_banco_preguntas", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cant_preguntas_estudiante", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_limite_tiempo", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_programada", Timestamp.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_tema_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_tipo_prueba_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_nombre", examenDTO.getNombre());
        query.setParameter("p_descripcion", examenDTO.getDescripcion());
        query.setParameter("p_categoria", examenDTO.getCategoria());
        query.setParameter("p_cant_banco_preguntas", examenDTO.getCantBancoPreguntas() != null ? examenDTO.getCantBancoPreguntas() : 0);
        query.setParameter("p_cant_preguntas_estudiante", examenDTO.getCantPreguntasEstudiante() != null ? examenDTO.getCantPreguntasEstudiante() : 0);
        query.setParameter("p_limite_tiempo", examenDTO.getLimiteTiempo());
        query.setParameter("p_fecha_programada", examenDTO.getFechaProgramada() != null ? new Timestamp(examenDTO.getFechaProgramada().getTime()) : null);
        query.setParameter("p_profesor_id", examenDTO.getProfesorId());
        query.setParameter("p_tema_id", examenDTO.getTemaId());
        query.setParameter("p_tipo_prueba_id", examenDTO.getTipoPruebaId());
        query.setParameter("p_curso_id", examenDTO.getCursoId());

        query.execute();

        Long examenId = (Long) query.getOutputParameterValue("p_examen_id");
        examenDTO.setExamenId(examenId);

        return examenDTO;
    }

    /**
     * Actualiza un examen existente utilizando el procedimiento almacenado PACTUALIZAR_EXAMEN_COMPLETO
     */
    @Transactional
    public ExamenDTO actualizarExamen(Long id, ExamenDTO examenDTO) {
        if (!examenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PACTUALIZAR_EXAMEN_COMPLETO")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_descripcion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_categoria", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cant_banco_preguntas", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cant_preguntas_estudiante", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_limite_tiempo", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_programada", Timestamp.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_tema_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_tipo_prueba_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_examen_id", id);
        query.setParameter("p_nombre", examenDTO.getNombre());
        query.setParameter("p_descripcion", examenDTO.getDescripcion());
        query.setParameter("p_categoria", examenDTO.getCategoria());
        query.setParameter("p_cant_banco_preguntas", examenDTO.getCantBancoPreguntas() != null ? examenDTO.getCantBancoPreguntas() : 0);
        query.setParameter("p_cant_preguntas_estudiante", examenDTO.getCantPreguntasEstudiante() != null ? examenDTO.getCantPreguntasEstudiante() : 0);
        query.setParameter("p_limite_tiempo", examenDTO.getLimiteTiempo());
        query.setParameter("p_fecha_programada", examenDTO.getFechaProgramada() != null ? new Timestamp(examenDTO.getFechaProgramada().getTime()) : null);
        query.setParameter("p_profesor_id", examenDTO.getProfesorId());
        query.setParameter("p_tema_id", examenDTO.getTemaId());
        query.setParameter("p_tipo_prueba_id", examenDTO.getTipoPruebaId());
        query.setParameter("p_curso_id", examenDTO.getCursoId());

        query.execute();

        examenDTO.setExamenId(id);
        return examenDTO;
    }

    /**
     * Elimina un examen utilizando el procedimiento almacenado PELIMINAR_EXAMEN_COMPLETO
     */
    @Transactional
    public void eliminarExamen(Long id) {
        if (!examenRepository.existsById(id)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PELIMINAR_EXAMEN_COMPLETO")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN);

        query.setParameter("p_examen_id", id);
        query.execute();
    }

    /**
     * Configura un examen utilizando el procedimiento almacenado PCONFIGURAR_EXAMEN
     */
    @Transactional
    public ConfiguracionExamenDTO configurarExamen(ConfiguracionExamenDTO configuracionDTO) {
        if (!examenRepository.existsById(configuracionDTO.getExamenId())) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + configuracionDTO.getExamenId());
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PCONFIGURAR_EXAMEN")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_peso", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_umbral", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_hora", Timestamp.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cant_preguntas", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_modo_seleccion", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_limite_tiempo", Integer.class, ParameterMode.IN);

        query.setParameter("p_examen_id", configuracionDTO.getExamenId());
        query.setParameter("p_peso", configuracionDTO.getPeso());
        query.setParameter("p_umbral", configuracionDTO.getUmbral());
        query.setParameter("p_fecha_hora", configuracionDTO.getFechaHora() != null ? new Timestamp(configuracionDTO.getFechaHora().getTime()) : null);
        query.setParameter("p_cant_preguntas", configuracionDTO.getCantPreguntas());
        query.setParameter("p_modo_seleccion", configuracionDTO.getModoSeleccion());
        query.setParameter("p_limite_tiempo", configuracionDTO.getLimiteTiempo());

        query.execute();

        return configuracionDTO;
    }

    /**
     * Asigna preguntas aleatorias a un examen utilizando el procedimiento ASIGNAR_PREGUNTAS_ALEATORIAS
     */
    @Transactional
    public void asignarPreguntasAleatorias(Long examenId, Integer cantidad, Long temaId) {
        if (!examenRepository.existsById(examenId)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + examenId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("ASIGNAR_PREGUNTAS_ALEATORIAS")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cantidad", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_tema_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_dificultad_id", Long.class, ParameterMode.IN);

        query.setParameter("p_examen_id", examenId);
        query.setParameter("p_cantidad", cantidad);
        query.setParameter("p_tema_id", temaId);
        query.setParameter("p_dificultad_id", null); // Opcional

        query.execute();
    }

    /**
     * Califica un examen utilizando la función CALIFICAR_EXAMEN
     */
    @Transactional
    public BigDecimal calificarExamen(Long envioId) {
        String sql = "SELECT CALIFICAR_EXAMEN(:envioId) FROM DUAL";

        jakarta.persistence.Query query = entityManager.createNativeQuery(sql);
        Parameter<Integer> param = query.getParameter(1, Integer.class);
        query.setParameter(param, envioId.intValue());

        Number result = (Number) query.getSingleResult();
        return BigDecimal.valueOf(result.doubleValue());
    }

    /**
     * Agrega una pregunta a un examen utilizando el procedimiento almacenado PK_EXAMEN.PAGREGAR_PREGUNTA_EXAMEN
     */
    @Transactional
    public void agregarPreguntaExamen(Long examenId, Long preguntaId, BigDecimal porcentajeExamen) {
        // Verificamos que el examen existe
        if (!examenRepository.existsById(examenId)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + examenId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PAGREGAR_PREGUNTA_EXAMEN")
                .registerStoredProcedureParameter("examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pregunta_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("porcentaje_examen", BigDecimal.class, ParameterMode.IN);

        query.setParameter("examen_id", examenId);
        query.setParameter("pregunta_id", preguntaId);
        query.setParameter("porcentaje_examen", porcentajeExamen);

        query.execute();
    }

    /**
     * Elimina una pregunta de un examen utilizando el procedimiento almacenado PK_EXAMEN.PREMOVER_PREGUNTA_EXAMEN
     */
    @Transactional
    public void eliminarPreguntaExamen(Long examenId, Long preguntaId) {
        // Verificamos que el examen existe
        if (!examenRepository.existsById(examenId)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + examenId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PREMOVER_PREGUNTA_EXAMEN")
                .registerStoredProcedureParameter("examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pregunta_id", Long.class, ParameterMode.IN);

        query.setParameter("examen_id", examenId);
        query.setParameter("pregunta_id", preguntaId);

        query.execute();
    }

    /**
     * Selecciona preguntas aleatorias para un examen utilizando el procedimiento almacenado PK_EXAMEN.PSELECCIONAR_PREGUNTAS_ALEATORIAS
     */
    @Transactional
    public void seleccionarPreguntasAleatorias(Long examenId, Integer cantidad, Long temaId) {
        // Verificamos que el examen existe
        if (!examenRepository.existsById(examenId)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + examenId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PSELECCIONAR_PREGUNTAS_ALEATORIAS")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cantidad", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_tema_id", Long.class, ParameterMode.IN);

        query.setParameter("p_examen_id", examenId);
        query.setParameter("p_cantidad", cantidad);
        query.setParameter("p_tema_id", temaId);

        query.execute();
    }

    /**
     * Obtiene un examen por su ID
     */
    @Transactional(readOnly = true)
    public ExamenDTO getExamenPorId(Long id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examen no encontrado con id: " + id));

        return modelMapper.map(examen, ExamenDTO.class);
    }

    /**
     * Obtiene todos los exámenes
     */
    @Transactional(readOnly = true)
    public List<ExamenDTO> listarTodosLosExamenes() {
        List<Examen> examenes = examenRepository.findAll();
        return examenes.stream()
                .map(examen -> modelMapper.map(examen, ExamenDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene exámenes por profesor utilizando la función PK_EXAMEN.POBTENER_EXAMENES_PROFESOR
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerExamenesPorProfesor(Long profesorId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.POBTENER_EXAMENES_PROFESOR", "ExamenesMapping")
                .registerStoredProcedureParameter("p_profesor_id", Long.class, ParameterMode.IN);

        query.setParameter("p_profesor_id", profesorId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<Map<String, Object>> examenes = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> examen = new HashMap<>();
            examen.put("examenId", row[0]);
            examen.put("nombre", row[1]);
            examen.put("descripcion", row[2]);
            examen.put("categoria", row[3]);
            examen.put("cantBancoPreguntas", row[4]);
            examen.put("cantPreguntasEstudiante", row[5]);
            examen.put("limiteTiempo", row[6]);
            examen.put("fechaProgramada", row[7]);
            examen.put("profesorId", row[8]);
            examen.put("temaId", row[9]);
            examen.put("temaNombre", row[10]);
            examen.put("tipoPruebaId", row[11]);
            examen.put("tipoPruebaNombre", row[12]);
            examen.put("cursoId", row[13]);
            examen.put("cursoNombre", row[14]);
            examenes.add(examen);
        }

        return examenes;
    }

    /**
     * Obtiene exámenes por curso utilizando la función POBTENER_EXAMENES_CURSO
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerExamenesPorCurso(Long cursoId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("POBTENER_EXAMENES_CURSO")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);

        query.setParameter("p_curso_id", cursoId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<Map<String, Object>> examenes = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> examen = new HashMap<>();
            examen.put("examenId", row[0]);
            examen.put("nombre", row[1]);
            examen.put("descripcion", row[2]);
            examen.put("categoria", row[3]);
            examen.put("cantBancoPreguntas", row[4]);
            examen.put("cantPreguntasEstudiante", row[5]);
            examen.put("limiteTiempo", row[6]);
            examen.put("fechaProgramada", row[7]);
            examen.put("profesorId", row[8]);
            examen.put("temaId", row[9]);
            examen.put("temaNombre", row[10]);
            examen.put("tipoPruebaId", row[11]);
            examen.put("tipoPruebaNombre", row[12]);
            examen.put("cursoId", row[13]);
            examen.put("cursoNombre", row[14]);
            examenes.add(examen);
        }

        return examenes;
    }

    /**
     * Obtiene exámenes disponibles para un estudiante utilizando la función PK_EXAMEN.POBTENER_EXAMENES_ESTUDIANTE
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerExamenesEstudiante(Long estudianteId, Long cursoId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.POBTENER_EXAMENES_ESTUDIANTE", "ExamenesEstudianteMapping")
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_estudiante_id", estudianteId);
        query.setParameter("p_curso_id", cursoId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<Map<String, Object>> examenes = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> examen = new HashMap<>();
            examen.put("examenId", row[0]);
            examen.put("nombre", row[1]);
            examen.put("descripcion", row[2]);
            examen.put("categoria", row[3]);
            examen.put("cantBancoPreguntas", row[4]);
            examen.put("cantPreguntasEstudiante", row[5]);
            examen.put("limiteTiempo", row[6]);
            examen.put("fechaProgramada", row[7]);
            examen.put("profesorId", row[8]);
            examen.put("temaId", row[9]);
            examen.put("temaNombre", row[10]);
            examen.put("tipoPruebaId", row[11]);
            examen.put("tipoPruebaNombre", row[12]);
            examen.put("cursoId", row[13]);
            examen.put("cursoNombre", row[14]);
            examenes.add(examen);
        }

        return examenes;
    }

    /**
     * Obtiene las preguntas para un examen utilizando la función PK_EXAMEN.POBTENER_PREGUNTAS_EXAMEN
     */
    @Transactional(readOnly = true)
    public List<PreguntaDTO> obtenerPreguntasExamen(Long examenId, Long estudianteId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.POBTENER_PREGUNTAS_EXAMEN", "PreguntasExamenMapping")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN);

        query.setParameter("p_examen_id", examenId);
        query.setParameter("p_estudiante_id", estudianteId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<PreguntaDTO> preguntas = new ArrayList<>();
        for (Object[] row : resultList) {
            PreguntaDTO pregunta = new PreguntaDTO();
            pregunta.setPreguntaId((Long) row[0]);
            pregunta.setTexto((String) row[1]);
            pregunta.setTipo((String) row[2]);
            pregunta.setPorcentaje((BigDecimal) row[3]);
            pregunta.setDificultad((String) row[4]);
            pregunta.setTiempoMaximo((Integer) row[5]);
            pregunta.setEsPublica((Integer) row[6]);
            pregunta.setTemaId((Long) row[7]);
            pregunta.setProfesorId((Long) row[8]);
            pregunta.setPreguntaPadreId((Long) row[9]);

            preguntas.add(pregunta);
        }

        return preguntas;
    }

    /**
     * Registra un nuevo envío de examen utilizando el procedimiento PK_EXAMEN.PREGISTRAR_ENVIO_EXAMEN
     */
    @Transactional
    public EnvioExamenDTO registrarEnvioExamen(Long examenId, Long estudianteId, String ip) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PREGISTRAR_ENVIO_EXAMEN")
                .registerStoredProcedureParameter("examen_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("estudiante_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ip", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("envio_id", Long.class, ParameterMode.OUT);

        query.setParameter("examen_id", examenId);
        query.setParameter("estudiante_id", estudianteId);
        query.setParameter("ip", ip);

        query.execute();

        Long envioId = (Long) query.getOutputParameterValue("envio_id");

        EnvioExamenDTO envioDTO = new EnvioExamenDTO();
        envioDTO.setEnvioId(envioId);
        envioDTO.setExamenId(examenId);
        envioDTO.setEstudianteId(estudianteId);
        envioDTO.setIp(ip);
        envioDTO.setFechaInicio(new Date());

        return envioDTO;
    }

    /**
     * Finaliza un envío de examen utilizando el procedimiento PK_EXAMEN.PFINALIZAR_ENVIO_EXAMEN
     */
    @Transactional
    public void finalizarEnvioExamen(Long envioId, BigDecimal puntaje) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PFINALIZAR_ENVIO_EXAMEN")
                .registerStoredProcedureParameter("envio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("puntaje", BigDecimal.class, ParameterMode.IN);

        query.setParameter("envio_id", envioId);
        query.setParameter("puntaje", puntaje);

        query.execute();
    }

    /**
     * Obtiene estadísticas por examen utilizando la función PK_EXAMEN.PESTADISTICAS_EXAMEN
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasExamen(Long examenId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PESTADISTICAS_EXAMEN", "EstadisticasExamenMapping")
                .registerStoredProcedureParameter("p_examen_id", Long.class, ParameterMode.IN);

        query.setParameter("p_examen_id", examenId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron estadísticas para el examen con id: " + examenId);
        }

        Object[] row = resultList.get(0);

        return Map.of(
                "examenId", row[0],
                "nombre", row[1],
                "descripcion", row[2],
                "totalPresentaciones", row[3],
                "promedioPuntaje", row[4],
                "minimoPuntaje", row[5],
                "maximoPuntaje", row[6],
                "aprobados", row[7],
                "reprobados", row[8],
                "promedioTiempoMinutos", row[9]
        );
    }

    /**
     * Obtiene estadísticas por tema utilizando la función PK_EXAMEN.PESTADISTICAS_TEMA
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasTema(Long temaId, Long cursoId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PESTADISTICAS_TEMA", "EstadisticasTemaMapping")
                .registerStoredProcedureParameter("p_tema_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_tema_id", temaId);
        query.setParameter("p_curso_id", cursoId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron estadísticas para el tema con id: " + temaId);
        }

        Object[] row = resultList.get(0);

        return Map.of(
                "temaId", row[0],
                "temaNombre", row[1],
                "totalExamenes", row[2],
                "totalPresentaciones", row[3],
                "promedioPuntaje", row[4],
                "totalPreguntas", row[5],
                "totalRespuestas", row[6]
        );
    }

    /**
     * Obtiene estadísticas por grupo (curso) utilizando la función PK_EXAMEN.PESTADISTICAS_GRUPO
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasGrupo(Long cursoId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PESTADISTICAS_GRUPO", "EstadisticasGrupoMapping")
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_curso_id", cursoId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron estadísticas para el curso con id: " + cursoId);
        }

        Object[] row = resultList.get(0);

        return Map.of(
                "cursoId", row[0],
                "cursoNombre", row[1],
                "totalExamenes", row[2],
                "totalPresentaciones", row[3],
                "promedioPuntaje", row[4],
                "minimoPuntaje", row[5],
                "maximoPuntaje", row[6],
                "totalEstudiantes", row[7],
                "estudiantesAprobados", row[8]
        );
    }

    /**
     * Obtiene estadísticas por estudiante utilizando la función PK_EXAMEN.PESTADISTICAS_ESTUDIANTE
     */
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasEstudiante(Long estudianteId, Long cursoId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_EXAMEN.PESTADISTICAS_ESTUDIANTE", "EstadisticasEstudianteMapping")
                .registerStoredProcedureParameter("p_estudiante_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_curso_id", Long.class, ParameterMode.IN);

        query.setParameter("p_estudiante_id", estudianteId);
        query.setParameter("p_curso_id", cursoId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron estadísticas para el estudiante con id: " + estudianteId);
        }

        Object[] row = resultList.get(0);

        return Map.of(
                "usuarioId", row[0],
                "nombre", row[1],
                "apellido", row[2],
                "totalExamenesPresentados", row[3],
                "promedioPuntaje", row[4],
                "examenesAprobados", row[5],
                "examenesReprobados", row[6],
                "totalRespuestasCorrectas", row[7],
                "totalRespuestasIncorrectas", row[8]
        );
    }
}