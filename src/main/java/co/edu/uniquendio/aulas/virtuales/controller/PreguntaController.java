package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import co.edu.uniquendio.aulas.virtuales.dto.PreguntaDTO;
import co.edu.uniquendio.aulas.virtuales.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    /**
     * Crea una nueva pregunta
     */
    @PostMapping
    public ResponseEntity<MensajeDTO<PreguntaDTO>> crearPregunta(@Valid @RequestBody PreguntaDTO preguntaDTO) {
        try {
            PreguntaDTO nuevaPregunta = preguntaService.crearPregunta(preguntaDTO);
            MensajeDTO<PreguntaDTO> respuesta = new MensajeDTO<>(false, nuevaPregunta);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<PreguntaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Actualiza una pregunta existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<PreguntaDTO>> actualizarPregunta(@PathVariable Long id,
                                                                      @Valid @RequestBody PreguntaDTO preguntaDTO) {
        try {
            PreguntaDTO preguntaActualizada = preguntaService.actualizarPregunta(id, preguntaDTO);
            MensajeDTO<PreguntaDTO> respuesta = new MensajeDTO<>(false, preguntaActualizada);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<PreguntaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una pregunta por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarPregunta(@PathVariable Long id) {
        try {
            preguntaService.eliminarPregunta(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtiene una pregunta por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<PreguntaDTO>> getPreguntaPorId(@PathVariable Long id) {
        try {
            PreguntaDTO pregunta = preguntaService.getPreguntaPorId(id);
            MensajeDTO<PreguntaDTO> respuesta = new MensajeDTO<>(false, pregunta);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<PreguntaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lista todas las preguntas
     */
    @GetMapping
    public ResponseEntity<MensajeDTO<List<PreguntaDTO>>> listarTodasLasPreguntas() {
        try {
            List<PreguntaDTO> preguntas = preguntaService.listarTodasLasPreguntas();
            MensajeDTO<List<PreguntaDTO>> respuesta = new MensajeDTO<>(false, preguntas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<PreguntaDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lista todas las preguntas de un profesor específico
     */
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<MensajeDTO<List<PreguntaDTO>>> listarPreguntasPorProfesor(@PathVariable Long profesorId) {
        try {
            List<PreguntaDTO> preguntas = preguntaService.listarPreguntasPorProfesor(profesorId);
            MensajeDTO<List<PreguntaDTO>> respuesta = new MensajeDTO<>(false, preguntas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<PreguntaDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Verifica si existe una pregunta
     */
    @GetMapping("/existe")
    public ResponseEntity<MensajeDTO<Boolean>> existePregunta(@RequestParam Long profesorId,
                                                              @RequestParam Long preguntaPadreId) {
        try {
            boolean existe = preguntaService.existePregunta(profesorId, preguntaPadreId);
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(false, existe);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}