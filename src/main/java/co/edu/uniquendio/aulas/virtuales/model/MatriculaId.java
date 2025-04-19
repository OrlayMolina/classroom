package co.edu.uniquendio.aulas.virtuales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaId implements Serializable {
    private Long cursoId;
    private Long estudianteId;
}