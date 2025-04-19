package co.edu.uniquendio.aulas.virtuales.service;

import co.edu.uniquendio.aulas.virtuales.dto.OpcionPreguntaDTO;
import co.edu.uniquendio.aulas.virtuales.exception.ResourceNotFoundException;
import co.edu.uniquendio.aulas.virtuales.model.OpcionPregunta;
import co.edu.uniquendio.aulas.virtuales.model.Pregunta;
import co.edu.uniquendio.aulas.virtuales.repository.OpcionPreguntaRepository;
import co.edu.uniquendio.aulas.virtuales.repository.PreguntaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpcionPreguntaService {

    @PersistenceContext
    private EntityManager entityManager;

    private final OpcionPreguntaRepository opcionPreguntaRepository;
    private final PreguntaRepository preguntaRepository;
    private final ModelMapper modelMapper;

    /**
     * Crea una nueva opción de pregunta
     */
    @Transactional
    public OpcionPreguntaDTO crearOpcionPregunta(OpcionPreguntaDTO opcionDTO) {
        Pregunta pregunta = preguntaRepository.findById(opcionDTO.getPreguntaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con id: " + opcionDTO.getPreguntaId()));

        // Usar secuencia SEQ_OPCION para generar ID
        Long opcionId = (Long) entityManager.createNativeQuery("SELECT SEQ_OPCION.NEXTVAL FROM DUAL").getSingleResult();

        OpcionPregunta opcion = new OpcionPregunta();
        opcion.setOpcionId(opcionId);
        opcion.setTexto(opcionDTO.getTexto());
        opcion.setEsCorrecta(opcionDTO.getEsCorrecta());
        opcion.setPregunta(pregunta);

        opcion = opcionPreguntaRepository.save(opcion);

        opcionDTO.setOpcionId(opcion.getOpcionId());
        return opcionDTO;
    }

    /**
     * Actualiza una opción de pregunta existente
     */
    @Transactional
    public OpcionPreguntaDTO actualizarOpcionPregunta(Long id, OpcionPreguntaDTO opcionDTO) {
        OpcionPregunta opcion = opcionPreguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Opción no encontrada con id: " + id));

        // Si cambia la pregunta asociada, la buscamos
        if (!opcion.getPregunta().getPreguntaId().equals(opcionDTO.getPreguntaId())) {
            Pregunta nuevaPregunta = preguntaRepository.findById(opcionDTO.getPreguntaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada con id: " + opcionDTO.getPreguntaId()));
            opcion.setPregunta(nuevaPregunta);
        }

        opcion.setTexto(opcionDTO.getTexto());
        opcion.setEsCorrecta(opcionDTO.getEsCorrecta());

        opcion = opcionPreguntaRepository.save(opcion);

        return modelMapper.map(opcion, OpcionPreguntaDTO.class);
    }

    /**
     * Elimina una opción de pregunta por su ID
     */
    @Transactional
    public void eliminarOpcionPregunta(Long id) {
        if (!opcionPreguntaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Opción no encontrada con id: " + id);
        }
        opcionPreguntaRepository.deleteById(id);
    }

    /**
     * Obtiene una opción de pregunta por su ID
     */
    @Transactional(readOnly = true)
    public OpcionPreguntaDTO getOpcionPreguntaPorId(Long id) {
        OpcionPregunta opcion = opcionPreguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Opción no encontrada con id: " + id));

        return modelMapper.map(opcion, OpcionPreguntaDTO.class);
    }

    /**
     * Lista todas las opciones de una pregunta específica
     */
    @Transactional(readOnly = true)
    public List<OpcionPreguntaDTO> listarOpcionesPorPregunta(Long preguntaId) {
        // Verificamos que la pregunta existe
        if (!preguntaRepository.existsById(preguntaId)) {
            throw new ResourceNotFoundException("Pregunta no encontrada con id: " + preguntaId);
        }

        List<OpcionPregunta> opciones = opcionPreguntaRepository.findByPreguntaPreguntaId(preguntaId);
        return opciones.stream()
                .map(opcion -> modelMapper.map(opcion, OpcionPreguntaDTO.class))
                .collect(Collectors.toList());
    }
}