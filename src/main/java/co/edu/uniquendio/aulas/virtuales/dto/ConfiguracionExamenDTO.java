package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionExamenDTO {

    private Long configId;

    @NotNull(message = "El peso del examen es obligatorio")
    @Min(value = 0, message = "El peso no puede ser negativo")
    @Max(value = 100, message = "El peso no puede ser mayor a 100")
    private BigDecimal peso;

    @NotNull(message = "El umbral de aprobación es obligatorio")
    @Min(value = 0, message = "El umbral no puede ser negativo")
    @Max(value = 100, message = "El umbral no puede ser mayor a 100")
    private BigDecimal umbral;

    private Date fechaHora;

    @NotNull(message = "La cantidad de preguntas es obligatoria")
    @Min(value = 1, message = "Debe haber al menos una pregunta")
    private Integer cantPreguntas;

    @Size(max = 20, message = "El modo de selección no puede superar los 20 caracteres")
    private String modoSeleccion;

    private Integer limiteTiempo;

    @NotNull(message = "El ID del examen es obligatorio")
    private Long examenId;
}
