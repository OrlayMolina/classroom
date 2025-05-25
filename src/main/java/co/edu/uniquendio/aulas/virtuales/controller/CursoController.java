package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.CursoDTO;
import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import co.edu.uniquendio.aulas.virtuales.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<MensajeDTO<CursoDTO>> crearCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            CursoDTO nuevoCurso = cursoService.crearCurso(cursoDTO);
            MensajeDTO<CursoDTO> respuesta = new MensajeDTO<>(false, nuevoCurso);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<CursoDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<CursoDTO>> actualizarCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        try {
            CursoDTO actualizadoCurso = cursoService.actualizarCurso(id, cursoDTO);
            MensajeDTO<CursoDTO> respuesta = new MensajeDTO<>(false, actualizadoCurso);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<CursoDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarCurso(@PathVariable Long id) {
        try {
            cursoService.eliminarCurso(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<CursoDTO>> obtenerCursoPorId(@PathVariable Long id) {
        try {
            CursoDTO curso = cursoService.getCursoPorId(id);
            MensajeDTO<CursoDTO> respuesta = new MensajeDTO<>(false, curso);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<CursoDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<CursoDTO>>> listarCursos() {
        try {
            List<CursoDTO> cursos = cursoService.listarTodosLosCursos();
            MensajeDTO<List<CursoDTO>> respuesta = new MensajeDTO<>(false, cursos);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<CursoDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<MensajeDTO<List<CursoDTO>>> listarCursosPorProfesor(@PathVariable Long profesorId) {
        try {
            List<CursoDTO> cursos = cursoService.obtenerCursosProfesor(profesorId);
            MensajeDTO<List<CursoDTO>> respuesta = new MensajeDTO<>(false, cursos);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            MensajeDTO<List<CursoDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<MensajeDTO<List<CursoDTO>>> listarCursosPorEstudiante(@PathVariable Long estudianteId) {
        try {
            List<CursoDTO> cursos = cursoService.obtenerCursosEstudiante(estudianteId);
            MensajeDTO<List<CursoDTO>> respuesta = new MensajeDTO<>(false, cursos);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<CursoDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{cursoId}/matricular/{estudianteId}")
    public ResponseEntity<MensajeDTO<Void>> matricularEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            cursoService.matricularEstudiante(cursoId, estudianteId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cursoId}/retirar/{estudianteId}")
    public ResponseEntity<MensajeDTO<Void>> retirarEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            cursoService.retirarEstudiante(cursoId, estudianteId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cursoId}/estudiante/{estudianteId}/matriculado")
    public ResponseEntity<MensajeDTO<Boolean>> verificarMatriculaEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            boolean estaMatriculado = cursoService.estaMatriculado(cursoId, estudianteId);
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(false, estaMatriculado);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}