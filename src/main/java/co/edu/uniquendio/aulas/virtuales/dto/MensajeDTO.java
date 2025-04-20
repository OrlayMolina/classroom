package co.edu.uniquendio.aulas.virtuales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeDTO<T> {
    private boolean error;
    private T respuesta;
}
