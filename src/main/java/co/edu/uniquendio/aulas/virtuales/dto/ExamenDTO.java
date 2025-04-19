package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenDTO {

    private Long examenId;

    @NotBlank(message = "El nombre del examen es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String descripcion;

    @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
    private String categoria;

    private Integer cantBancoPreguntas;

    private Integer cantPreguntasEstudiante;

    private Integer limiteTiempo;

    private Date fechaProgramada;

    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId;

    @NotNull(message = "El ID del tema es obligatorio")
    private Long temaId;

    @NotNull(message = "El ID del tipo de prueba es obligatorio")
    private Long tipoPruebaId;

    @NotNull(message = "El ID del curso es obligatorio")
    private Long cursoId;

    // Campos adicionales para mostrar información relacionada
    private String nombreProfesor;
    private String apellidoProfesor;
    private String nombreTema;
    private String nombreTipoPrueba;
    private String nombreCurso;
}
