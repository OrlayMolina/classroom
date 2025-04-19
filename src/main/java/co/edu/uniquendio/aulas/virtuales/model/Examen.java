package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXAMEN")
public class Examen {

    @Id
    @Column(name = "EXAMEN_ID")
    private Long examenId;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 1000)
    private String descripcion;

    @Column(name = "CATEGORIA", length = 100)
    private String categoria;

    @Column(name = "CANT_BANCO_PREGUNTAS")
    private Integer cantBancoPreguntas;

    @Column(name = "CANT_PREGUNTAS_ESTUDIANTE")
    private Integer cantPreguntasEstudiante;

    @Column(name = "LIMITE_TIEMPO")
    private Integer limiteTiempo;

    @Column(name = "FECHA_PROGRAMADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramada;

    @ManyToOne
    @JoinColumn(name = "PROFESOR_ID")
    private Usuario profesor;

    @ManyToOne
    @JoinColumn(name = "TEMA_ID")
    private Tema tema;

    @ManyToOne
    @JoinColumn(name = "TIPO_PRUEBA_ID")
    private TipoPrueba tipoPrueba;

    @ManyToOne
    @JoinColumn(name = "CURSO_ID")
    private Curso curso;

    @OneToOne(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConfiguracionExamen configuracion;

    @OneToMany(mappedBy = "examen")
    private List<EnvioExamen> envios;

    @ManyToMany
    @JoinTable(
            name = "EXAMEN_PREGUNTA",
            joinColumns = @JoinColumn(name = "EXAMEN_ID"),
            inverseJoinColumns = @JoinColumn(name = "PREGUNTA_ID")
    )
    private List<Pregunta> preguntas;
}