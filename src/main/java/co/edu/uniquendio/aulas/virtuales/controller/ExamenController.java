package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.ConfiguracionExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.EnvioExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.ExamenDTO;
import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
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
public class ExamenController {

    private final ExamenService examenService;
    private final PreguntaService preguntaService;

    @PostMapping
    public ResponseEntity<MensajeDTO<ExamenDTO>> crearExamen(@RequestBody ExamenDTO examenDTO) {
        try {
            ExamenDTO nuevoExamen = examenService.crearExamen(examenDTO);
            MensajeDTO<ExamenDTO> respuesta = new MensajeDTO<>(false, nuevoExamen);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<ExamenDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<ExamenDTO>> actualizarExamen(@PathVariable Long id, @RequestBody ExamenDTO examenDTO) {
        try {
            ExamenDTO actualizadoExamen = examenService.actualizarExamen(id, examenDTO);
            MensajeDTO<ExamenDTO> respuesta = new MensajeDTO<>(false, actualizadoExamen);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<ExamenDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarExamen(@PathVariable Long id) {
        try {
            examenService.eliminarExamen(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<ExamenDTO>> obtenerExamenPorId(@PathVariable Long id) {
        try {
            ExamenDTO examen = examenService.getExamenPorId(id);
            MensajeDTO<ExamenDTO> respuesta = new MensajeDTO<>(false, examen);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<ExamenDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<ExamenDTO>>> listarExamenes() {
        try {
            List<ExamenDTO> examenes = examenService.listarTodosLosExamenes();
            MensajeDTO<List<ExamenDTO>> respuesta = new MensajeDTO<>(false, examenes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<ExamenDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<MensajeDTO<List<Map<String, Object>>>> listarExamenesPorProfesor(@PathVariable Long profesorId) {
        try {
            List<Map<String, Object>> examenes = examenService.obtenerExamenesPorProfesor(profesorId);
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(false, examenes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<MensajeDTO<List<Map<String, Object>>>> listarExamenesPorCurso(@PathVariable Long cursoId) {
        try {
            List<Map<String, Object>> examenes = examenService.obtenerExamenesPorCurso(cursoId);
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(false, examenes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<MensajeDTO<List<Map<String, Object>>>> listarExamenesEstudiante(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        try {
            List<Map<String, Object>> examenes = examenService.obtenerExamenesEstudiante(estudianteId, cursoId);
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(false, examenes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{examenId}/configuracion")
    public ResponseEntity<MensajeDTO<ConfiguracionExamenDTO>> configurarExamen(
            @PathVariable Long examenId,
            @RequestBody ConfiguracionExamenDTO configuracionDTO) {
        try {
            configuracionDTO.setExamenId(examenId);
            ConfiguracionExamenDTO configuracion = examenService.configurarExamen(configuracionDTO);
            MensajeDTO<ConfiguracionExamenDTO> respuesta = new MensajeDTO<>(false, configuracion);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<ConfiguracionExamenDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{examenId}/preguntas/{preguntaId}")
    public ResponseEntity<MensajeDTO<Void>> agregarPreguntaExamen(
            @PathVariable Long examenId,
            @PathVariable Long preguntaId,
            @RequestParam(defaultValue = "0") BigDecimal porcentaje) {
        try {
            examenService.agregarPreguntaExamen(examenId, preguntaId, porcentaje);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{examenId}/preguntas/{preguntaId}")
    public ResponseEntity<MensajeDTO<Void>> eliminarPreguntaExamen(
            @PathVariable Long examenId,
            @PathVariable Long preguntaId) {
        try {
            examenService.eliminarPreguntaExamen(examenId, preguntaId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{examenId}/preguntas/aleatorias")
    public ResponseEntity<MensajeDTO<Void>> seleccionarPreguntasAleatorias(
            @PathVariable Long examenId,
            @RequestParam Long temaId,
            @RequestParam Integer cantidad) {
        try {
            examenService.seleccionarPreguntasAleatorias(examenId, cantidad, temaId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{examenId}/preguntas/{estudianteId}")
    public ResponseEntity<MensajeDTO<List<PreguntaDTO>>> obtenerPreguntasExamen(
            @PathVariable Long examenId,
            @PathVariable Long estudianteId) {
        try {
            List<PreguntaDTO> preguntas = examenService.obtenerPreguntasExamen(examenId, estudianteId);
            MensajeDTO<List<PreguntaDTO>> respuesta = new MensajeDTO<>(false, preguntas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<PreguntaDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{examenId}/iniciar")
    public ResponseEntity<MensajeDTO<EnvioExamenDTO>> iniciarExamen(
            @PathVariable Long examenId,
            @RequestBody Map<String, Object> datos) {
        try {
            Long estudianteId = Long.parseLong(datos.get("estudianteId").toString());
            String ip = (String) datos.get("ip");
            EnvioExamenDTO envio = examenService.registrarEnvioExamen(examenId, estudianteId, ip);
            MensajeDTO<EnvioExamenDTO> respuesta = new MensajeDTO<>(false, envio);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<EnvioExamenDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/envios/{envioId}/finalizar")
    public ResponseEntity<MensajeDTO<Void>> finalizarExamen(
            @PathVariable Long envioId,
            @RequestBody Map<String, Object> datos) {
        try {
            BigDecimal puntaje = new BigDecimal(datos.get("puntaje").toString());
            examenService.finalizarEnvioExamen(envioId, puntaje);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{examenId}/estadisticas")
    public ResponseEntity<MensajeDTO<Map<String, Object>>> obtenerEstadisticasExamen(@PathVariable Long examenId) {
        try {
            Map<String, Object> estadisticas = examenService.obtenerEstadisticasExamen(examenId);
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(false, estadisticas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estadisticas/tema/{temaId}")
    public ResponseEntity<MensajeDTO<Map<String, Object>>> obtenerEstadisticasTema(
            @PathVariable Long temaId,
            @RequestParam(required = false) Long cursoId) {
        try {
            Map<String, Object> estadisticas = examenService.obtenerEstadisticasTema(temaId, cursoId);
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(false, estadisticas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estadisticas/curso/{cursoId}")
    public ResponseEntity<MensajeDTO<Map<String, Object>>> obtenerEstadisticasGrupo(@PathVariable Long cursoId) {
        try {
            Map<String, Object> estadisticas = examenService.obtenerEstadisticasGrupo(cursoId);
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(false, estadisticas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estadisticas/estudiante/{estudianteId}")
    public ResponseEntity<MensajeDTO<Map<String, Object>>> obtenerEstadisticasEstudiante(
            @PathVariable Long estudianteId,
            @RequestParam(required = false) Long cursoId) {
        try {
            Map<String, Object> estadisticas = examenService.obtenerEstadisticasEstudiante(estudianteId, cursoId);
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(false, estadisticas);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Map<String, Object>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}