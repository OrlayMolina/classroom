package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanEstudioDTO {

    private Long planEstudioId;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    /**
     * IDs de los contenidos asociados al plan de estudio.
     * Se usarán en la creación/actualización.
     */
    @NotNull(message = "La lista de IDs de contenidos es obligatoria")
    private List<Long> contenidoIds;

    /**
     * IDs de los cursos asociados al plan de estudio.
     * Se usarán en la creación/actualización.
     */
    @NotNull(message = "La lista de IDs de cursos es obligatoria")
    private List<Long> cursoIds;

    // Campos adicionales para mostrar información relacionada
    private Integer numeroContenidos;   // p. ej. contenidos.size()
    private Integer numeroCursos;       // p. ej. cursos.size()

    /**
     * Si necesitas exponer datos completos de las relaciones,
     * puedes incluir aquí los DTOs de Contenido y Curso.
     */
    private List<ContenidoDTO> contenidos;
    private List<CursoDTO> cursos;
}