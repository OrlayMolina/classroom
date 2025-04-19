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
public class ContenidoDTO {

    private Long contenidoId;

    @Size(max = 4000, message = "La descripción no puede superar los 4000 caracteres")
    private String descripcionContenido;

    @NotNull(message = "El ID de la unidad es obligatorio")
    private Long unidadId;

    // Campo opcional para mostrar el nombre de la unidad asociada
    private String nombreUnidad;

    /**
     * Para crear o actualizar relaciones con planes de estudio,
     * enviamos los IDs de los planes asociados.
     */
    private List<Long> planEstudioIds;

    // Campos adicionales para mostrar información relacionada
    private Integer numeroPlanesEstudio;  // por ejemplo, planEstudioIds.size()

    /**
     * Si necesitas exponer datos completos de los planes,
     * puedes inyectar aquí los DTOs de PlanEstudio
     */
    private List<PlanEstudioDTO> planesEstudio;
}
