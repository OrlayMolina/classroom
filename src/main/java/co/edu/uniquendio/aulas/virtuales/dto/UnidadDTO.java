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
public class UnidadDTO {

    private Long unidadId;

    @NotNull(message = "El nombre de la unidad es obligatorio")
    @Size(max = 50, message = "El nombre de la unidad no puede superar los 50 caracteres")
    private String nombreUnidad;

    /**
     * IDs de los contenidos asociados a esta unidad.
     * Se usan para creación/actualización.
     */
    private List<Long> contenidoIds;

    /**
     * Cantidad de contenidos asociados.
     * Útil para mostrar métricas rápidas.
     */
    private Integer numeroContenidos;

    /**
     * Si necesitas exponer los datos completos de cada contenido,
     * inyecta aquí los DTOs correspondientes.
     */
    private List<ContenidoDTO> contenidos;
}
