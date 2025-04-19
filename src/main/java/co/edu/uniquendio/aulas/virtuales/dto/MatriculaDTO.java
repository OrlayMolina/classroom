package co.edu.uniquendio.aulas.virtuales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDTO {
    private Long cursoId;
    private Long estudianteId;
    private Date fechaMatricula;

    // Campos adicionales para información del curso y estudiante
    private String nombreCurso;
    private String nombreEstudiante;
    private String apellidoEstudiante;
}