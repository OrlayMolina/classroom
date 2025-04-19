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
@Table(name = "PLAN_ESTUDIO")
public class PlanEstudio {

    @Id
    @Column(name = "PLAN_ESTUDIO_ID")
    private Long planEstudioId;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "PLAN_ESTUDIO_CONTENIDO",
            joinColumns = @JoinColumn(name = "PLAN_ESTUDIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTENIDO_ID")
    )
    private List<Contenido> contenidos;

    @OneToMany(mappedBy = "planEstudio")
    private List<Curso> cursos;
}