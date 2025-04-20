package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.MatriculaDTO;
import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import co.edu.uniquendio.aulas.virtuales.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<MensajeDTO<MatriculaDTO>> matricularEstudiante(@RequestBody MatriculaDTO matriculaDTO) {
        try {
            MatriculaDTO resultado = matriculaService.matricularEstudiante(matriculaDTO);
            MensajeDTO<MatriculaDTO> respuesta = new MensajeDTO<>(false, resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<MatriculaDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{cursoId}/{estudianteId}")
    public ResponseEntity<MensajeDTO<Void>> desmatricularEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            matriculaService.desmatricularEstudiante(cursoId, estudianteId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<MensajeDTO<List<MatriculaDTO>>> obtenerEstudiantesPorCurso(@PathVariable Long cursoId) {
        try {
            List<MatriculaDTO> estudiantes = matriculaService.obtenerEstudiantesPorCurso(cursoId);
            MensajeDTO<List<MatriculaDTO>> respuesta = new MensajeDTO<>(false, estudiantes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<MatriculaDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<MensajeDTO<List<MatriculaDTO>>> obtenerCursosPorEstudiante(@PathVariable Long estudianteId) {
        try {
            List<MatriculaDTO> cursos = matriculaService.obtenerCursosPorEstudiante(estudianteId);
            MensajeDTO<List<MatriculaDTO>> respuesta = new MensajeDTO<>(false, cursos);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<MatriculaDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/verificar/{cursoId}/{estudianteId}")
    public ResponseEntity<MensajeDTO<Boolean>> verificarMatricula(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            boolean estaMatriculado = matriculaService.verificarMatricula(cursoId, estudianteId);
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(false, estaMatriculado);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/contador/{cursoId}")
    public ResponseEntity<MensajeDTO<Integer>> contarEstudiantesPorCurso(@PathVariable Long cursoId) {
        try {
            Integer contador = matriculaService.contarEstudiantesPorCurso(cursoId);
            MensajeDTO<Integer> respuesta = new MensajeDTO<>(false, contador);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Integer> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}