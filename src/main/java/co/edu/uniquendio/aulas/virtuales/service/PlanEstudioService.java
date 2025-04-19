package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.ContenidoDTO;
import co.edu.uniquendio.aulas.virtuales.dto.PlanEstudioDTO;
import co.edu.uniquendio.aulas.virtuales.dto.UnidadDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.PlanEstudio;
import co.edu.uniquendio.aulas.virtuales.repository.PlanEstudioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanEstudioService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PlanEstudioRepository planEstudioRepository;
    private final ModelMapper modelMapper;

    /**
     * Crea un nuevo plan de estudio utilizando el procedimiento almacenado PK_PLAN_ESTUDIO.PCREAR_PLAN_ESTUDIO
     */
    @Transactional
    public PlanEstudioDTO crearPlanEstudio(PlanEstudioDTO planEstudioDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PCREAR_PLAN_ESTUDIO")
                .registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.OUT);

        query.setParameter("nombre", planEstudioDTO.getNombre());

        query.execute();

        Long planEstudioId = (Long) query.getOutputParameterValue("plan_estudio_id");
        planEstudioDTO.setPlanEstudioId(planEstudioId);

        return planEstudioDTO;
    }

    /**
     * Actualiza un plan de estudio existente utilizando el procedimiento almacenado PK_PLAN_ESTUDIO.PACTUALIZAR_PLAN_ESTUDIO
     */
    @Transactional
    public PlanEstudioDTO actualizarPlanEstudio(Long id, PlanEstudioDTO planEstudioDTO) {
        // Verificamos que el plan de estudio existe
        if (!planEstudioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PACTUALIZAR_PLAN_ESTUDIO")
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN);

        query.setParameter("plan_estudio_id", id);
        query.setParameter("nombre", planEstudioDTO.getNombre());

        query.execute();

        planEstudioDTO.setPlanEstudioId(id);
        return planEstudioDTO;
    }

    /**
     * Elimina un plan de estudio utilizando el procedimiento almacenado PK_PLAN_ESTUDIO.PELIMINAR_PLAN_ESTUDIO
     */
    @Transactional
    public void eliminarPlanEstudio(Long id) {
        // Verificamos que el plan de estudio existe
        if (!planEstudioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id: " + id);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PELIMINAR_PLAN_ESTUDIO")
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.IN);

        query.setParameter("plan_estudio_id", id);
        query.execute();
    }

    /**
     * Crea un nuevo contenido utilizando el procedimiento almacenado PK_PLAN_ESTUDIO.PCREAR_CONTENIDO
     */
    @Transactional
    public ContenidoDTO crearContenido(ContenidoDTO contenidoDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PCREAR_CONTENIDO")
                .registerStoredProcedureParameter("descripcion_contenido", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("unidad_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("contenido_id", Long.class, ParameterMode.OUT);

        query.setParameter("descripcion_contenido", contenidoDTO.getDescripcionContenido());
        query.setParameter("unidad_id", contenidoDTO.getUnidadId());

        query.execute();

        Long contenidoId = (Long) query.getOutputParameterValue("contenido_id");
        contenidoDTO.setContenidoId(contenidoId);

        return contenidoDTO;
    }

    /**
     * Actualiza un contenido existente utilizando el procedimiento almacenado PK_PLAN_ESTUDIO.PACTUALIZAR_CONTENIDO
     */
    @Transactional
    public ContenidoDTO actualizarContenido(Long id, ContenidoDTO contenidoDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PACTUALIZAR_CONTENIDO")
                .registerStoredProcedureParameter("contenido_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("descripcion_contenido", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("unidad_id", Long.class, ParameterMode.IN);

        query.setParameter("contenido_id", id);
        query.setParameter("descripcion_contenido", contenidoDTO.getDescripcionContenido());
        query.setParameter("unidad_id", contenidoDTO.getUnidadId());

        query.execute();

        contenidoDTO.setContenidoId(id);
        return contenidoDTO;
    }

    /**
     * Elimina un contenido utilizando el procedimiento almacenado PK_PLAN_ESTUDIO.PELIMINAR_CONTENIDO
     */
    @Transactional
    public void eliminarContenido(Long id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PELIMINAR_CONTENIDO")
                .registerStoredProcedureParameter("contenido_id", Long.class, ParameterMode.IN);

        query.setParameter("contenido_id", id);
        query.execute();
    }

    /**
     * Agrega un contenido a un plan de estudio utilizando el procedimiento PK_PLAN_ESTUDIO.PAGREGAR_CONTENIDO_PLAN
     */
    @Transactional
    public void agregarContenidoPlan(Long planEstudioId, Long contenidoId) {
        // Verificamos que el plan de estudio existe
        if (!planEstudioRepository.existsById(planEstudioId)) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id: " + planEstudioId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PAGREGAR_CONTENIDO_PLAN")
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("contenido_id", Long.class, ParameterMode.IN);

        query.setParameter("plan_estudio_id", planEstudioId);
        query.setParameter("contenido_id", contenidoId);

        query.execute();
    }

    /**
     * Elimina un contenido de un plan de estudio utilizando el procedimiento PK_PLAN_ESTUDIO.PREMOVER_CONTENIDO_PLAN
     */
    @Transactional
    public void eliminarContenidoPlan(Long planEstudioId, Long contenidoId) {
        // Verificamos que el plan de estudio existe
        if (!planEstudioRepository.existsById(planEstudioId)) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id: " + planEstudioId);
        }

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.PREMOVER_CONTENIDO_PLAN")
                .registerStoredProcedureParameter("plan_estudio_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("contenido_id", Long.class, ParameterMode.IN);

        query.setParameter("plan_estudio_id", planEstudioId);
        query.setParameter("contenido_id", contenidoId);

        query.execute();
    }

    /**
     * Obtiene un plan de estudio completo utilizando la función PK_PLAN_ESTUDIO.POBTENER_PLAN_ESTUDIO
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerPlanEstudio(Long planEstudioId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.POBTENER_PLAN_ESTUDIO", "PlanEstudioMapping")
                .registerStoredProcedureParameter("p_plan_estudio_id", Long.class, ParameterMode.IN);

        query.setParameter("p_plan_estudio_id", planEstudioId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<Map<String, Object>> contenidos = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> contenido = Map.of(
                    "planEstudioId", row[0],
                    "planNombre", row[1],
                    "unidadId", row[2],
                    "nombreUnidad", row[3],
                    "contenidoId", row[4],
                    "descripcionContenido", row[5]
            );
            contenidos.add(contenido);
        }

        return contenidos;
    }

    /**
     * Obtiene los contenidos de un plan de estudio utilizando la función PK_PLAN_ESTUDIO.POBTENER_CONTENIDOS_PLAN
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerContenidosPlan(Long planEstudioId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.POBTENER_CONTENIDOS_PLAN", "ContenidosPlanMapping")
                .registerStoredProcedureParameter("p_plan_estudio_id", Long.class, ParameterMode.IN);

        query.setParameter("p_plan_estudio_id", planEstudioId);

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<Map<String, Object>> contenidos = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> contenido = Map.of(
                    "contenidoId", row[0],
                    "descripcionContenido", row[1],
                    "unidadId", row[2],
                    "nombreUnidad", row[3]
            );
            contenidos.add(contenido);
        }

        return contenidos;
    }

    /**
     * Obtiene todas las unidades utilizando la función PK_PLAN_ESTUDIO.POBTENER_UNIDADES
     */
    @Transactional(readOnly = true)
    public List<UnidadDTO> obtenerUnidades() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PLAN_ESTUDIO.POBTENER_UNIDADES", "UnidadesMapping");

        query.execute();

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        List<UnidadDTO> unidades = new ArrayList<>();
        for (Object[] row : resultList) {
            UnidadDTO unidad = new UnidadDTO();
            unidad.setUnidadId((Long) row[0]);
            unidad.setNombreUnidad((String) row[1]);

            unidades.add(unidad);
        }

        return unidades;
    }

    /**
     * Obtiene un plan de estudio por su ID
     */
    @Transactional(readOnly = true)
    public PlanEstudioDTO getPlanEstudioPorId(Long id) {
        PlanEstudio planEstudio = planEstudioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan de estudio no encontrado con id: " + id));

        return modelMapper.map(planEstudio, PlanEstudioDTO.class);
    }

    /**
     * Obtiene todos los planes de estudio
     */
    @Transactional(readOnly = true)
    public List<PlanEstudioDTO> listarTodosLosPlanes() {
        List<PlanEstudio> planes = planEstudioRepository.findAll();
        return planes.stream()
                .map(plan -> modelMapper.map(plan, PlanEstudioDTO.class))
                .collect(Collectors.toList());
    }
}
