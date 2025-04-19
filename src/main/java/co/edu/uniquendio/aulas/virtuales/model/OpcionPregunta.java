package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OPCION_PREGUNTA")
public class OpcionPregunta {

    @Id
    @Column(name = "OPCION_ID")
    private Long opcionId;

    @Column(name = "TEXTO", nullable = false, length = 1000)
    private String texto;

    @Column(name = "ES_CORRECTA")
    private Integer esCorrecta;

    @ManyToOne
    @JoinColumn(name = "PREGUNTA_ID")
    private Pregunta pregunta;
}