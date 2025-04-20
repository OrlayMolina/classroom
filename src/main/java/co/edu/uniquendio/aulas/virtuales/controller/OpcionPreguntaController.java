package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
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
@CrossOrigin(origins = "*")
public class OpcionPreguntaController {

    private final OpcionPreguntaService opcionPreguntaService;

    /**
     * Crea una nueva opción de pregunta
     */
    @PostMapping
    public ResponseEntity<MensajeDTO<OpcionPreguntaDTO>> crearOpcionPregunta(@Valid @RequestBody OpcionPreguntaDTO opcionDTO) {
        try {
            OpcionPreguntaDTO nuevaOpcion = opcionPreguntaService.crearOpcionPregunta(opcionDTO);
            MensajeDTO<OpcionPreguntaDTO> respuesta = new MensajeDTO<>(false, nuevaOpcion);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<OpcionPreguntaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Actualiza una opción de pregunta existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<OpcionPreguntaDTO>> actualizarOpcionPregunta(@PathVariable Long id,
                                                                                  @Valid @RequestBody OpcionPreguntaDTO opcionDTO) {
        try {
            OpcionPreguntaDTO opcionActualizada = opcionPreguntaService.actualizarOpcionPregunta(id, opcionDTO);
            MensajeDTO<OpcionPreguntaDTO> respuesta = new MensajeDTO<>(false, opcionActualizada);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<OpcionPreguntaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una opción de pregunta por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarOpcionPregunta(@PathVariable Long id) {
        try {
            opcionPreguntaService.eliminarOpcionPregunta(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtiene una opción de pregunta por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<OpcionPreguntaDTO>> getOpcionPreguntaPorId(@PathVariable Long id) {
        try {
            OpcionPreguntaDTO opcion = opcionPreguntaService.getOpcionPreguntaPorId(id);
            MensajeDTO<OpcionPreguntaDTO> respuesta = new MensajeDTO<>(false, opcion);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<OpcionPreguntaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lista todas las opciones de una pregunta específica
     */
    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<MensajeDTO<List<OpcionPreguntaDTO>>> listarOpcionesPorPregunta(@PathVariable Long preguntaId) {
        try {
            List<OpcionPreguntaDTO> opciones = opcionPreguntaService.listarOpcionesPorPregunta(preguntaId);
            MensajeDTO<List<OpcionPreguntaDTO>> respuesta = new MensajeDTO<>(false, opciones);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<OpcionPreguntaDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}