package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import co.edu.uniquendio.aulas.virtuales.dto.RolDTO;
import co.edu.uniquendio.aulas.virtuales.dto.UsuarioDTO;
import co.edu.uniquendio.aulas.virtuales.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<MensajeDTO<UsuarioDTO>> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);
            MensajeDTO<UsuarioDTO> respuesta = new MensajeDTO<>(false, nuevoUsuario);
            return ResponseEntity.ok(respuesta);
        }catch (Exception e) {
            MensajeDTO<UsuarioDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO actualizadoUsuario = usuarioService.actualizarUsuario(id, usuarioDTO);
            MensajeDTO<UsuarioDTO> respuesta = new MensajeDTO<>(false, actualizadoUsuario);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<UsuarioDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<Void>> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            MensajeDTO<Void> respuesta = new MensajeDTO<>(false, null);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Void> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.getUsuarioPorId(id);
            MensajeDTO<UsuarioDTO> respuesta = new MensajeDTO<>(false, usuario);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<UsuarioDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.listarTodosLosUsuarios();
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(false, usuarios);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profesores")
    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarProfesores() {
        try {
            List<UsuarioDTO> profesores = usuarioService.listarProfesores();
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(false, profesores);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/estudiantes")
    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarEstudiantes() {
        try {
            List<UsuarioDTO> estudiantes = usuarioService.listarEstudiantes();
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(false, estudiantes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/curso/{cursoId}/estudiantes")
    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarEstudiantesPorCurso(@PathVariable Long cursoId) {
        try {
            List<UsuarioDTO> estudiantes = usuarioService.obtenerEstudiantesCurso(cursoId);
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(false, estudiantes);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<List<UsuarioDTO>> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/es-profesor")
    public ResponseEntity<MensajeDTO<Boolean>> esProfesor(@PathVariable Long id) {
        try {
            boolean esProfesor = usuarioService.esProfesor(id);
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(false, esProfesor);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/es-estudiante")
    public ResponseEntity<MensajeDTO<Boolean>> esEstudiante(@PathVariable Long id) {
        try {
            boolean esEstudiante = usuarioService.esEstudiante(id);
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(false, esEstudiante);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<Boolean> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<MensajeDTO<RolDTO>> usuarioRol(@PathVariable Long id) {
        try {
            RolDTO rolDTO = usuarioService.obtenerNombreRolUsuario(id);
            MensajeDTO<RolDTO> respuesta = new MensajeDTO<>(false, rolDTO);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<RolDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
    }
}