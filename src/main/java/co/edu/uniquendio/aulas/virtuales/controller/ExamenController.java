package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.ConfiguracionExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.EnvioExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.ExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.PreguntaDTO;
import co.edu.uniquendio.aulas.virtuales.service.ExamenService;
import co.edu.uniquendio.aulas.virtuales.service.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExamenController {

    private final ExamenService examenService;
    private final PreguntaService preguntaService;

    @PostMapping
    public ResponseEntity<ExamenDTO> crearExamen(@RequestBody ExamenDTO examenDTO) {
        ExamenDTO nuevoExamen = examenService.crearExamen(examenDTO);
        return new ResponseEntity<>(nuevoExamen, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamenDTO> actualizarExamen(@PathVariable Long id, @RequestBody ExamenDTO examenDTO) {
        ExamenDTO actualizadoExamen = examenService.actualizarExamen(id, examenDTO);
        return ResponseEntity.ok(actualizadoExamen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExamen(@PathVariable Long id) {
        examenService.eliminarExamen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenDTO> obtenerExamenPorId(@PathVariable Long id) {
        ExamenDTO examen = examenService.getExamenPorId(id);
        return ResponseEntity.ok(examen);
    }

    @GetMapping
    public ResponseEntity<List<ExamenDTO>> listarExamenes() {
        List<ExamenDTO> examenes = examenService.listarTodosLosExamenes();
        return ResponseEntity.ok(examenes);
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<Map<String, Object>>> listarExamenesPorProfesor(@PathVariable Long profesorId) {
        List<Map<String, Object>> examenes = examenService.obtenerExamenesPorProfesor(profesorId);
        return ResponseEntity.ok(examenes);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Map<String, Object>>> listarExamenesPorCurso(@PathVariable Long cursoId) {
        List<Map<String, Object>> examenes = examenService.obtenerExamenesPorCurso(cursoId);
        return ResponseEntity.ok(examenes);
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<List<Map<String, Object>>> listarExamenesEstudiante(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        List<Map<String, Object>> examenes = examenService.obtenerExamenesEstudiante(estudianteId, cursoId);
        return ResponseEntity.ok(examenes);
    }

    @PostMapping("/{examenId}/configuracion")
    public ResponseEntity<ConfiguracionExamenDTO> configurarExamen(
            @PathVariable Long examenId,
            @RequestBody ConfiguracionExamenDTO configuracionDTO) {
        configuracionDTO.setExamenId(examenId);
        ConfiguracionExamenDTO configuracion = examenService.configurarExamen(configuracionDTO);
        return ResponseEntity.ok(configuracion);
    }

    @PostMapping("/{examenId}/preguntas/{preguntaId}")
    public ResponseEntity<Void> agregarPreguntaExamen(
            @PathVariable Long examenId,
            @PathVariable Long preguntaId,
            @RequestParam(defaultValue = "0") BigDecimal porcentaje) {
        examenService.agregarPreguntaExamen(examenId, preguntaId, porcentaje);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{examenId}/preguntas/{preguntaId}")
    public ResponseEntity<Void> eliminarPreguntaExamen(
            @PathVariable Long examenId,
            @PathVariable Long preguntaId) {
        examenService.eliminarPreguntaExamen(examenId, preguntaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{examenId}/preguntas/aleatorias")
    public ResponseEntity<Void> seleccionarPreguntasAleatorias(
            @PathVariable Long examenId,
            @RequestParam Long temaId,
            @RequestParam Integer cantidad) {
        examenService.seleccionarPreguntasAleatorias(examenId, cantidad, temaId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{examenId}/preguntas/{estudianteId}")
    public ResponseEntity<List<PreguntaDTO>> obtenerPreguntasExamen(
            @PathVariable Long examenId,
            @PathVariable Long estudianteId) {
        List<PreguntaDTO> preguntas = examenService.obtenerPreguntasExamen(examenId, estudianteId);
        return ResponseEntity.ok(preguntas);
    }

    @PostMapping("/{examenId}/iniciar")
    public ResponseEntity<EnvioExamenDTO> iniciarExamen(
            @PathVariable Long examenId,
            @RequestBody Map<String, Object> datos) {
        Long estudianteId = Long.parseLong(datos.get("estudianteId").toString());
        String ip = (String) datos.get("ip");
        EnvioExamenDTO envio = examenService.registrarEnvioExamen(examenId, estudianteId, ip);
        return new ResponseEntity<>(envio, HttpStatus.CREATED);
    }

    @PutMapping("/envios/{envioId}/finalizar")
    public ResponseEntity<Void> finalizarExamen(
            @PathVariable Long envioId,
            @RequestBody Map<String, Object> datos) {
        BigDecimal puntaje = new BigDecimal(datos.get("puntaje").toString());
        examenService.finalizarEnvioExamen(envioId, puntaje);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{examenId}/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasExamen(@PathVariable Long examenId) {
        Map<String, Object> estadisticas = examenService.obtenerEstadisticasExamen(examenId);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estadisticas/tema/{temaId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasTema(
            @PathVariable Long temaId,
            @RequestParam(required = false) Long cursoId) {
        Map<String, Object> estadisticas = examenService.obtenerEstadisticasTema(temaId, cursoId);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estadisticas/curso/{cursoId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasGrupo(@PathVariable Long cursoId) {
        Map<String, Object> estadisticas = examenService.obtenerEstadisticasGrupo(cursoId);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estadisticas/estudiante/{estudianteId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasEstudiante(
            @PathVariable Long estudianteId,
            @RequestParam(required = false) Long cursoId) {
        Map<String, Object> estadisticas = examenService.obtenerEstadisticasEstudiante(estudianteId, cursoId);
        return ResponseEntity.ok(estadisticas);
    }
}