package co.edu.uniquendio.aulas.virtuales.controller;

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
@CrossOrigin(origins = "*") // Para desarrollo, en producción se debe limitar
public class PreguntaController {

    private final PreguntaService preguntaService;

    /**
     * Crea una nueva pregunta
     */
    @PostMapping
    public ResponseEntity<PreguntaDTO> crearPregunta(@Valid @RequestBody PreguntaDTO preguntaDTO) {
        PreguntaDTO nuevaPregunta = preguntaService.crearPregunta(preguntaDTO);
        return new ResponseEntity<>(nuevaPregunta, HttpStatus.CREATED);
    }

    /**
     * Actualiza una pregunta existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaDTO> actualizarPregunta(@PathVariable Long id,
                                                          @Valid @RequestBody PreguntaDTO preguntaDTO) {
        PreguntaDTO preguntaActualizada = preguntaService.actualizarPregunta(id, preguntaDTO);
        return ResponseEntity.ok(preguntaActualizada);
    }

    /**
     * Elimina una pregunta por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Long id) {
        preguntaService.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una pregunta por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO> getPreguntaPorId(@PathVariable Long id) {
        PreguntaDTO pregunta = preguntaService.getPreguntaPorId(id);
        return ResponseEntity.ok(pregunta);
    }

    /**
     * Lista todas las preguntas
     */
    @GetMapping
    public ResponseEntity<List<PreguntaDTO>> listarTodasLasPreguntas() {
        List<PreguntaDTO> preguntas = preguntaService.listarTodasLasPreguntas();
        return ResponseEntity.ok(preguntas);
    }

    /**
     * Lista todas las preguntas de un profesor específico
     */
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<PreguntaDTO>> listarPreguntasPorProfesor(@PathVariable Long profesorId) {
        List<PreguntaDTO> preguntas = preguntaService.listarPreguntasPorProfesor(profesorId);
        return ResponseEntity.ok(preguntas);
    }

    /**
     * Verifica si existe una pregunta
     */
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existePregunta(@RequestParam Long profesorId,
                                                  @RequestParam Long preguntaPadreId) {
        boolean existe = preguntaService.existePregunta(profesorId, preguntaPadreId);
        return ResponseEntity.ok(existe);
    }
}