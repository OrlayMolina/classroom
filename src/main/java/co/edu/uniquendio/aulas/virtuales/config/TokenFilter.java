package co.edu.uniquendio.aulas.virtuales.config;

import co.edu.uniquendio.aulas.virtuales.dto.MensajeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String requestURI = request.getRequestURI();
            String token = getToken(request);
            boolean error = false;

            boolean requiereAutenticacion = requestURI.startsWith("/api/usuarios") ||
                    requestURI.startsWith("/api/cursos") ||
                    requestURI.startsWith("/api/examenes") ||
                    requestURI.startsWith("/api/matriculas") ||
                    requestURI.startsWith("/api/opciones") ||
                    requestURI.startsWith("/api/planes-estudio") ||
                    requestURI.startsWith("/api/preguntas");

            if (requiereAutenticacion) {
                try {
                    if (token != null) {
                        jwtUtils.parseJwt(token);
                    } else {
                        error = true;
                        crearRespuestaError("Se requiere autenticación para acceder a este recurso",
                                HttpServletResponse.SC_UNAUTHORIZED, response);
                    }
                } catch (MalformedJwtException | SignatureException e) {
                    error = true;
                    crearRespuestaError("El token es incorrecto",
                            HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
                } catch (ExpiredJwtException e) {
                    error = true;
                    crearRespuestaError("El token está vencido",
                            HttpServletResponse.SC_UNAUTHORIZED, response);
                } catch (Exception e) {
                    error = true;
                    crearRespuestaError(e.getMessage(),
                            HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
                }
            }

            if (!error) {
                filterChain.doFilter(request, response);
            }
        }
    }

    private String getToken (HttpServletRequest req){
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
    }

    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response)throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);
        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();
    }

    private boolean validarToken(String token){
        boolean error = true;
        if (token != null) {
            Jws<Claims> jws = jwtUtils.parseJwt(token);
            error = false;
        }
        return error;
    }

}

