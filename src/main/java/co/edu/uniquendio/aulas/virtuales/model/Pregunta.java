package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PREGUNTA")
public class Pregunta {

    @Id
    @Column(name = "PREGUNTA_ID")
    private Long preguntaId;

    @Column(name = "TEXTO", nullable = false, length = 2000)
    private String texto;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PORCENTAJE", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "DIFICULTAD", length = 20)
    private String dificultad;

    @Column(name = "TIEMPO_MAXIMO")
    private Integer tiempoMaximo;

    @Column(name = "ES_PUBLICA")
    private Integer esPublica;

    @ManyToOne
    @JoinColumn(name = "TEMA_ID")
    private Tema tema;

    @ManyToOne
    @JoinColumn(name = "PROFESOR_ID")
    private Usuario profesor;

    @ManyToOne
    @JoinColumn(name = "PREGUNTA_PADRE_ID")
    private Pregunta preguntaPadre;

    @OneToMany(mappedBy = "preguntaPadre")
    private List<Pregunta> subpreguntas;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcionPregunta> opciones;
}