package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {

    private Long cursoId;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotNull(message = "El ID del plan de estudio es obligatorio")
    private Long planEstudioId;

    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId;

    @NotNull(message = "El ID del horario es obligatorio")
    private Long horarioId;

    @NotNull(message = "El ID del día es obligatorio")
    private Long diaId;

    // Campos adicionales para mostrar información relacionada
    private String nombrePlanEstudio;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String horaInicio;
    private String horaFin;
    private String ubicacion;
    private String nombreDia;
}