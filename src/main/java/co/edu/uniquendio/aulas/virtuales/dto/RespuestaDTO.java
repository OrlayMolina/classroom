package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO {

    private Long respuestaId;

    @Size(max = 2000, message = "La respuesta no puede superar los 2000 caracteres")
    private String respuesta;

    @NotNull(message = "El ID del envío es obligatorio")
    private Long envioId;

    @NotNull(message = "El ID de la pregunta es obligatorio")
    private Long preguntaId;

    // Campos adicionales para mostrar información relacionada
    private String textoPregunta;
    private boolean esCorrecta;
}