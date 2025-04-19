package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.PreguntaDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.Pregunta;
import co.edu.uniquendio.aulas.virtuales.repository.PreguntaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreguntaService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PreguntaRepository preguntaRepository;
    private final ModelMapper modelMapper;

    /**
     * Crea una nueva pregunta utilizando el procedimiento almacenado PK_PREGUNTA.PCREAR_PREGUNTA
     */
    @Transactional
    public PreguntaDTO crearPregunta(PreguntaDTO preguntaDTO) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PREGUNTA.PCREAR_PREGUNTA")
                .registerStoredProcedureParameter("texto", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("porcentaje", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("dificultad", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tiempo_maximo", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("es_publica", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tema_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pregunta_padre_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pregunta_id", Long.class, ParameterMode.OUT);

        query.setParameter("texto", preguntaDTO.getTexto());
        query.setParameter("tipo", preguntaDTO.getTipo());
        query.setParameter("porcentaje", preguntaDTO.getPorcentaje());
        query.setParameter("dificultad", preguntaDTO.getDificultad());
        query.setParameter("tiempo_maximo", preguntaDTO.getTiempoMaximo());
        query.setParameter("es_publica", preguntaDTO.getEsPublica());
        query.setParameter("tema_id", preguntaDTO.getTemaId());
        query.setParameter("profesor_id", preguntaDTO.getProfesorId());
        query.setParameter("pregunta_padre_id", preguntaDTO.getPreguntaPadreId());

        query.execute();

        Long preguntaId = (Long) query.getOutputParameterValue("pregunta_id");
        preguntaDTO.setPreguntaId(preguntaId);

        return preguntaDTO;
    }

    /**
     * Actualiza una pregunta utilizando el procedimiento almacenado PK_PREGUNTA.PACTUALIZAR_PREGUNTA
     */
    @Transactional
    public PreguntaDTO actualizarPregunta(Long id, PreguntaDTO preguntaDTO) {
        // Primero verificamos que la pregunta existe
        Pregunta preguntaExistente = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con id: " + id));

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PREGUNTA.PACTUALIZAR_PREGUNTA")
                .registerStoredProcedureParameter("pregunta_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("texto", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("porcentaje", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("dificultad", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tiempo_maximo", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("es_publica", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("tema_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pregunta_padre_id", Long.class, ParameterMode.IN);

        query.setParameter("pregunta_id", id);
        query.setParameter("texto", preguntaDTO.getTexto());
        query.setParameter("tipo", preguntaDTO.getTipo());
        query.setParameter("porcentaje", preguntaDTO.getPorcentaje());
        query.setParameter("dificultad", preguntaDTO.getDificultad());
        query.setParameter("tiempo_maximo", preguntaDTO.getTiempoMaximo());
        query.setParameter("es_publica", preguntaDTO.getEsPublica());
        query.setParameter("tema_id", preguntaDTO.getTemaId());
        query.setParameter("profesor_id", preguntaDTO.getProfesorId());
        query.setParameter("pregunta_padre_id", preguntaDTO.getPreguntaPadreId());

        query.execute();

        preguntaDTO.setPreguntaId(id);
        return preguntaDTO;
    }

    /**
     * Elimina una pregunta utilizando el procedimiento almacenado PK_PREGUNTA.PELIMINAR_PREGUNTA
     */
    @Transactional
    public void eliminarPregunta(Long id) {
        // Primero verificamos que la pregunta existe
        Pregunta preguntaExistente = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con id: " + id));

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PREGUNTA.PELIMINAR_PREGUNTA")
                .registerStoredProcedureParameter("pregunta_id", Long.class, ParameterMode.IN);

        query.setParameter("pregunta_id", id);
        query.execute();
    }

    /**
     * Verifica si existe una pregunta usando la función PK_PREGUNTA.PEXISTE_PREGUNTA
     */
    @Transactional(readOnly = true)
    public boolean existePregunta(Long profesorId, Long preguntaPadreId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("PK_PREGUNTA.PEXISTE_PREGUNTA")
                .registerStoredProcedureParameter("profesor_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pregunta_padre_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("RETURN_VALUE", Boolean.class, ParameterMode.OUT);

        query.setParameter("profesor_id", profesorId);
        query.setParameter("pregunta_padre_id", preguntaPadreId);
        query.execute();

        return (Boolean) query.getOutputParameterValue("RETURN_VALUE");
    }

    /**
     * Busca una pregunta por su ID
     */
    @Transactional(readOnly = true)
    public PreguntaDTO getPreguntaPorId(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con id: " + id));

        return modelMapper.map(pregunta, PreguntaDTO.class);
    }

    /**
     * Lista todas las preguntas
     */
    @Transactional(readOnly = true)
    public List<PreguntaDTO> listarTodasLasPreguntas() {
        List<Pregunta> preguntas = preguntaRepository.findAll();
        return preguntas.stream()
                .map(pregunta -> modelMapper.map(pregunta, PreguntaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Lista todas las preguntas por profesor
     */
    @Transactional(readOnly = true)
    public List<PreguntaDTO> listarPreguntasPorProfesor(Long profesorId) {
        List<Pregunta> preguntas = preguntaRepository.findByProfesorUsuarioId(profesorId);
        return preguntas.stream()
                .map(pregunta -> modelMapper.map(pregunta, PreguntaDTO.class))
                .collect(Collectors.toList());
    }
}