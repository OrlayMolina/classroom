package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.MatriculaDTO;
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
    public ResponseEntity<MatriculaDTO> matricularEstudiante(@RequestBody MatriculaDTO matriculaDTO) {
        MatriculaDTO resultado = matriculaService.matricularEstudiante(matriculaDTO);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cursoId}/{estudianteId}")
    public ResponseEntity<Void> desmatricularEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        matriculaService.desmatricularEstudiante(cursoId, estudianteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<MatriculaDTO>> obtenerEstudiantesPorCurso(@PathVariable Long cursoId) {
        List<MatriculaDTO> estudiantes = matriculaService.obtenerEstudiantesPorCurso(cursoId);
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<MatriculaDTO>> obtenerCursosPorEstudiante(@PathVariable Long estudianteId) {
        List<MatriculaDTO> cursos = matriculaService.obtenerCursosPorEstudiante(estudianteId);
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/verificar/{cursoId}/{estudianteId}")
    public ResponseEntity<Boolean> verificarMatricula(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        boolean estaMatriculado = matriculaService.verificarMatricula(cursoId, estudianteId);
        return new ResponseEntity<>(estaMatriculado, HttpStatus.OK);
    }

    @GetMapping("/contador/{cursoId}")
    public ResponseEntity<Integer> contarEstudiantesPorCurso(@PathVariable Long cursoId) {
        Integer contador = matriculaService.contarEstudiantesPorCurso(cursoId);
        return new ResponseEntity<>(contador, HttpStatus.OK);
    }
}