package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.CursoDTO;
import co.edu.uniquendio.aulas.virtuales.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoDTO> crearCurso(@RequestBody CursoDTO cursoDTO) {
        CursoDTO nuevoCurso = cursoService.crearCurso(cursoDTO);
        return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        CursoDTO actualizadoCurso = cursoService.actualizarCurso(id, cursoDTO);
        return ResponseEntity.ok(actualizadoCurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerCursoPorId(@PathVariable Long id) {
        CursoDTO curso = cursoService.getCursoPorId(id);
        return ResponseEntity.ok(curso);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        List<CursoDTO> cursos = cursoService.listarTodosLosCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<CursoDTO>> listarCursosPorProfesor(@PathVariable Long profesorId) {
        List<CursoDTO> cursos = cursoService.obtenerCursosProfesor(profesorId);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<CursoDTO>> listarCursosPorEstudiante(@PathVariable Long estudianteId) {
        List<CursoDTO> cursos = cursoService.obtenerCursosEstudiante(estudianteId);
        return ResponseEntity.ok(cursos);
    }

    @PostMapping("/{cursoId}/matricular/{estudianteId}")
    public ResponseEntity<Void> matricularEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        cursoService.matricularEstudiante(cursoId, estudianteId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cursoId}/retirar/{estudianteId}")
    public ResponseEntity<Void> retirarEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        cursoService.retirarEstudiante(cursoId, estudianteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cursoId}/estudiante/{estudianteId}/matriculado")
    public ResponseEntity<Boolean> verificarMatriculaEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        boolean estaMatriculado = cursoService.estaMatriculado(cursoId, estudianteId);
        return ResponseEntity.ok(estaMatriculado);
    }
}