package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CURSO")
public class Curso {

    @Id
    @Column(name = "CURSO_ID")
    private Long cursoId;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "PLAN_ESTUDIO_ID")
    private PlanEstudio planEstudio;

    @ManyToOne
    @JoinColumn(name = "PROFESOR_ID")
    private Usuario profesor;

    @ManyToOne
    @JoinColumn(name = "HORARIO_ID")
    private Horario horario;

    @ManyToOne
    @JoinColumn(name = "DIA_ID")
    private DiaSemana dia;

    @OneToMany(mappedBy = "curso")
    private List<Examen> examenes;

    @ManyToMany
    @JoinTable(
            name = "MATRICULA",
            joinColumns = @JoinColumn(name = "CURSO_ID"),
            inverseJoinColumns = @JoinColumn(name = "ESTUDIANTE_ID")
    )
    private List<Usuario> estudiantes;
}
