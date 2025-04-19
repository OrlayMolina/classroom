package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.OpcionPreguntaDTO;
import co.edu.uniquendio.aulas.virtuales.service.OpcionPreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Para desarrollo, en producción se debe limitar
public class OpcionPreguntaController {

    private final OpcionPreguntaService opcionPreguntaService;

    /**
     * Crea una nueva opción de pregunta
     */
    @PostMapping
    public ResponseEntity<OpcionPreguntaDTO> crearOpcionPregunta(@Valid @RequestBody OpcionPreguntaDTO opcionDTO) {
        OpcionPreguntaDTO nuevaOpcion = opcionPreguntaService.crearOpcionPregunta(opcionDTO);
        return new ResponseEntity<>(nuevaOpcion, HttpStatus.CREATED);
    }

    /**
     * Actualiza una opción de pregunta existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<OpcionPreguntaDTO> actualizarOpcionPregunta(@PathVariable Long id,
                                                                      @Valid @RequestBody OpcionPreguntaDTO opcionDTO) {
        OpcionPreguntaDTO opcionActualizada = opcionPreguntaService.actualizarOpcionPregunta(id, opcionDTO);
        return ResponseEntity.ok(opcionActualizada);
    }

    /**
     * Elimina una opción de pregunta por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOpcionPregunta(@PathVariable Long id) {
        opcionPreguntaService.eliminarOpcionPregunta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una opción de pregunta por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpcionPreguntaDTO> getOpcionPreguntaPorId(@PathVariable Long id) {
        OpcionPreguntaDTO opcion = opcionPreguntaService.getOpcionPreguntaPorId(id);
        return ResponseEntity.ok(opcion);
    }

    /**
     * Lista todas las opciones de una pregunta específica
     */
    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<List<OpcionPreguntaDTO>> listarOpcionesPorPregunta(@PathVariable Long preguntaId) {
        List<OpcionPreguntaDTO> opciones = opcionPreguntaService.listarOpcionesPorPregunta(preguntaId);
        return ResponseEntity.ok(opciones);
    }
}
