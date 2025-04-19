package co.edu.uniquendio.aulas.virtuales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDTO {

    private Long preguntaId;

    @NotBlank(message = "El texto de la pregunta es obligatorio")
    @Size(max = 2000, message = "El texto no puede superar los 2000 caracteres")
    private String texto;

    @NotBlank(message = "El tipo de pregunta es obligatorio")
    @Size(max = 50, message = "El tipo no puede superar los 50 caracteres")
    private String tipo;

    private BigDecimal porcentaje;

    @Size(max = 20, message = "La dificultad no puede superar los 20 caracteres")
    private String dificultad;

    private Integer tiempoMaximo;

    private Integer esPublica;

    private Long temaId;

    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId;

    private Long preguntaPadreId;
}