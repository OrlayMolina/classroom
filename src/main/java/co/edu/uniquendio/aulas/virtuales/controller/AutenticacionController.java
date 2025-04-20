package co.edu.uniquendio.aulas.virtuales.controller;

import co.edu.uniquendio.aulas.virtuales.dto.LoginDTO;
import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import co.edu.uniquendio.aulas.virtuales.dto.TokenDTO;
import co.edu.uniquendio.aulas.virtuales.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacionController {

    private final UsuarioService usuarioService;

    @PostMapping("/autenticar")
    public ResponseEntity<MensajeDTO<TokenDTO>> autenticarUsuario(@RequestBody LoginDTO loginDTO) {
        try {

            TokenDTO token = usuarioService.autenticarUsuario(loginDTO);
            MensajeDTO<TokenDTO> respuesta = new MensajeDTO<>(false, token);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            MensajeDTO<TokenDTO> respuesta = new MensajeDTO<>(true, null);
            return new ResponseEntity<>(respuesta, HttpStatus.UNAUTHORIZED);
        }
    }
}
