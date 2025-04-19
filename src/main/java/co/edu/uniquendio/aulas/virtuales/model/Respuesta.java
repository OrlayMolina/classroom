package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESPUESTA")
public class Respuesta {

    @Id
    @Column(name = "RESPUESTA_ID")
    private Long respuestaId;

    @Column(name = "RESPUESTA", length = 2000)
    private String respuesta;

    @ManyToOne
    @JoinColumn(name = "ENVIO_ID")
    private EnvioExamen envio;

    @ManyToOne
    @JoinColumn(name = "PREGUNTA_ID")
    private Pregunta pregunta;
}