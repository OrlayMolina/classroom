package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.ContenidoDTO;
import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import co.edu.uniquendio.aulas.virtuales.dto.PlanEstudioDTO;
import co.edu.uniquendio.aulas.virtuales.dto.UnidadDTO;
import co.edu.uniquendio.aulas.virtuales.service.PlanEstudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planes-estudio")
@RequiredArgsConstructor
public class PlanEstudioController {

    private final PlanEstudioService planEstudioService;

    @PostMapping
    public ResponseEntity<MensajeDTO<PlanEstudioDTO>> crearPlanEstudio(@RequestBody PlanEstudioDTO planEstudioDTO) {
        try {
            PlanEstudioDTO nuevoPlan = planEstudioService.crearPlanEstudio(planEstudioDTO);
            MensajeDTO<PlanEstudioDTO> respuesta = new MensajeDTO<>(false, nuevoPlan);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<PlanEstudioDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<PlanEstudioDTO>> actualizarPlanEstudio(@PathVariable Long id, @RequestBody PlanEstudioDTO planEstudioDTO) {
        try {
            PlanEstudioDTO actualizadoPlan = planEstudioService.actualizarPlanEstudio(id, planEstudioDTO);
            MensajeDTO<PlanEstudioDTO> respuesta = new MensajeDTO<>(false, actualizadoPlan);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<PlanEstudioDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarPlanEstudio(@PathVariable Long id) {
        try {
            planEstudioService.eliminarPlanEstudio(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<PlanEstudioDTO>> obtenerPlanEstudioPorId(@PathVariable Long id) {
        try {
            PlanEstudioDTO planEstudio = planEstudioService.getPlanEstudioPorId(id);
            MensajeDTO<PlanEstudioDTO> respuesta = new MensajeDTO<>(false, planEstudio);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<PlanEstudioDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<PlanEstudioDTO>>> listarPlanesEstudio() {
        try {
            List<PlanEstudioDTO> planes = planEstudioService.listarTodosLosPlanes();
            MensajeDTO<List<PlanEstudioDTO>> respuesta = new MensajeDTO<>(false, planes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<PlanEstudioDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/completo")
    public ResponseEntity<MensajeDTO<List<Map<String, Object>>>> obtenerPlanEstudioCompleto(@PathVariable Long id) {
        try {
            List<Map<String, Object>> planCompleto = planEstudioService.obtenerPlanEstudio(id);
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(false, planCompleto);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/contenidos")
    public ResponseEntity<MensajeDTO<List<Map<String, Object>>>> obtenerContenidosPlan(@PathVariable Long id) {
        try {
            List<Map<String, Object>> contenidos = planEstudioService.obtenerContenidosPlan(id);
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(false, contenidos);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<Map<String, Object>>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/contenidos")
    public ResponseEntity<MensajeDTO<ContenidoDTO>> crearContenido(@RequestBody ContenidoDTO contenidoDTO) {
        try {
            ContenidoDTO nuevoContenido = planEstudioService.crearContenido(contenidoDTO);
            MensajeDTO<ContenidoDTO> respuesta = new MensajeDTO<>(false, nuevoContenido);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            MensajeDTO<ContenidoDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/contenidos/{id}")
    public ResponseEntity<MensajeDTO<ContenidoDTO>> actualizarContenido(@PathVariable Long id, @RequestBody ContenidoDTO contenidoDTO) {
        try {
            ContenidoDTO actualizadoContenido = planEstudioService.actualizarContenido(id, contenidoDTO);
            MensajeDTO<ContenidoDTO> respuesta = new MensajeDTO<>(false, actualizadoContenido);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<ContenidoDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/contenidos/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarContenido(@PathVariable Long id) {
        try {
            planEstudioService.eliminarContenido(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{planId}/contenidos/{contenidoId}")
    public ResponseEntity<MensajeDTO<Void>> agregarContenidoPlan(
            @PathVariable Long planId,
            @PathVariable Long contenidoId) {
        try {
            planEstudioService.agregarContenidoPlan(planId, contenidoId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{planId}/contenidos/{contenidoId}")
    public ResponseEntity<MensajeDTO<Void>> eliminarContenidoPlan(
            @PathVariable Long planId,
            @PathVariable Long contenidoId) {
        try {
            planEstudioService.eliminarContenidoPlan(planId, contenidoId);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/unidades")
    public ResponseEntity<MensajeDTO<List<UnidadDTO>>> listarUnidades() {
        try {
            List<UnidadDTO> unidades = planEstudioService.obtenerUnidades();
            MensajeDTO<List<UnidadDTO>> respuesta = new MensajeDTO<>(false, unidades);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<UnidadDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}