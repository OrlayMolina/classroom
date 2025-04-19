package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioExamenDTO {

    private Long envioId;

    private Date fechaInicio;

    private Date fechaFin;

    private BigDecimal puntaje;

    @Size(max = 50, message = "La IP no puede superar los 50 caracteres")
    private String ip;

    @NotNull(message = "El ID del examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    // Campos adicionales para mostrar información relacionada
    private String nombreExamen;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private Integer tiempoTotalMinutos;

    // Lista de respuestas del envío
    private List<RespuestaDTO> respuestas;
}