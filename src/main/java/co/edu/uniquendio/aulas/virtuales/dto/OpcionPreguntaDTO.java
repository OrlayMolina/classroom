package co.edu.uniquendio.aulas.virtuales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcionPreguntaDTO {

    private Long opcionId;

    @NotBlank(message = "El texto de la opción es obligatorio")
    @Size(max = 1000, message = "El texto no puede superar los 1000 caracteres")
    private String texto;

    @NotNull(message = "El indicador de respuesta correcta es obligatorio")
    private Integer esCorrecta;

    @NotNull(message = "El ID de la pregunta es obligatorio")
    private Long preguntaId;
}