package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.ContenidoDTO;
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
@CrossOrigin(origins = "*")
public class PlanEstudioController {

    private final PlanEstudioService planEstudioService;

    @PostMapping
    public ResponseEntity<PlanEstudioDTO> crearPlanEstudio(@RequestBody PlanEstudioDTO planEstudioDTO) {
        PlanEstudioDTO nuevoPlan = planEstudioService.crearPlanEstudio(planEstudioDTO);
        return new ResponseEntity<>(nuevoPlan, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanEstudioDTO> actualizarPlanEstudio(@PathVariable Long id, @RequestBody PlanEstudioDTO planEstudioDTO) {
        PlanEstudioDTO actualizadoPlan = planEstudioService.actualizarPlanEstudio(id, planEstudioDTO);
        return ResponseEntity.ok(actualizadoPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlanEstudio(@PathVariable Long id) {
        planEstudioService.eliminarPlanEstudio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanEstudioDTO> obtenerPlanEstudioPorId(@PathVariable Long id) {
        PlanEstudioDTO planEstudio = planEstudioService.getPlanEstudioPorId(id);
        return ResponseEntity.ok(planEstudio);
    }

    @GetMapping
    public ResponseEntity<List<PlanEstudioDTO>> listarPlanesEstudio() {
        List<PlanEstudioDTO> planes = planEstudioService.listarTodosLosPlanes();
        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}/completo")
    public ResponseEntity<List<Map<String, Object>>> obtenerPlanEstudioCompleto(@PathVariable Long id) {
        List<Map<String, Object>> planCompleto = planEstudioService.obtenerPlanEstudio(id);
        return ResponseEntity.ok(planCompleto);
    }

    @GetMapping("/{id}/contenidos")
    public ResponseEntity<List<Map<String, Object>>> obtenerContenidosPlan(@PathVariable Long id) {
        List<Map<String, Object>> contenidos = planEstudioService.obtenerContenidosPlan(id);
        return ResponseEntity.ok(contenidos);
    }

    @PostMapping("/contenidos")
    public ResponseEntity<ContenidoDTO> crearContenido(@RequestBody ContenidoDTO contenidoDTO) {
        ContenidoDTO nuevoContenido = planEstudioService.crearContenido(contenidoDTO);
        return new ResponseEntity<>(nuevoContenido, HttpStatus.CREATED);
    }

    @PutMapping("/contenidos/{id}")
    public ResponseEntity<ContenidoDTO> actualizarContenido(@PathVariable Long id, @RequestBody ContenidoDTO contenidoDTO) {
        ContenidoDTO actualizadoContenido = planEstudioService.actualizarContenido(id, contenidoDTO);
        return ResponseEntity.ok(actualizadoContenido);
    }

    @DeleteMapping("/contenidos/{id}")
    public ResponseEntity<Void> eliminarContenido(@PathVariable Long id) {
        planEstudioService.eliminarContenido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{planId}/contenidos/{contenidoId}")
    public ResponseEntity<Void> agregarContenidoPlan(
            @PathVariable Long planId,
            @PathVariable Long contenidoId) {
        planEstudioService.agregarContenidoPlan(planId, contenidoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{planId}/contenidos/{contenidoId}")
    public ResponseEntity<Void> eliminarContenidoPlan(
            @PathVariable Long planId,
            @PathVariable Long contenidoId) {
        planEstudioService.eliminarContenidoPlan(planId, contenidoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unidades")
    public ResponseEntity<List<UnidadDTO>> listarUnidades() {
        List<UnidadDTO> unidades = planEstudioService.obtenerUnidades();
        return ResponseEntity.ok(unidades);
    }
}