package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.UsuarioDTO;
import co.edu.uniquendio.aulas.virtuales.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO actualizadoUsuario = usuarioService.actualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(actualizadoUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.getUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/profesores")
    public ResponseEntity<List<UsuarioDTO>> listarProfesores() {
        List<UsuarioDTO> profesores = usuarioService.listarProfesores();
        return ResponseEntity.ok(profesores);
    }

    @GetMapping("/estudiantes")
    public ResponseEntity<List<UsuarioDTO>> listarEstudiantes() {
        List<UsuarioDTO> estudiantes = usuarioService.listarEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/curso/{cursoId}/estudiantes")
    public ResponseEntity<List<UsuarioDTO>> listarEstudiantesPorCurso(@PathVariable Long cursoId) {
        List<UsuarioDTO> estudiantes = usuarioService.obtenerEstudiantesCurso(cursoId);
        return ResponseEntity.ok(estudiantes);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<UsuarioDTO> autenticarUsuario(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String clave = credenciales.get("clave");

        UsuarioDTO usuario = usuarioService.autenticarUsuario(email, clave);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{id}/es-profesor")
    public ResponseEntity<Boolean> esProfesor(@PathVariable Long id) {
        boolean esProfesor = usuarioService.esProfesor(id);
        return ResponseEntity.ok(esProfesor);
    }

    @GetMapping("/{id}/es-estudiante")
    public ResponseEntity<Boolean> esEstudiante(@PathVariable Long id) {
        boolean esEstudiante = usuarioService.esEstudiante(id);
        return ResponseEntity.ok(esEstudiante);
    }
}