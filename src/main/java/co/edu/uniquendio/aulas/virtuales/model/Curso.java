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
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Curso.findAllCursos",
                query = "SELECT c.CURSO_ID, c.NOMBRE, c.PLAN_ESTUDIO_ID, c.PROFESOR_ID, " +
                        "h.HORARIO_ID, d.DIA_ID, h.HORA_INICIO, h.HORA_FIN, h.UBICACION, " +
                        "d.NOMBRE as NOMBRE_DIA, p.NOMBRE as PLAN_NOMBRE " +
                        "FROM CURSO c " +
                        "JOIN HORARIO h ON c.HORARIO_ID = h.HORARIO_ID " +
                        "JOIN DIA_SEMANA d ON c.DIA_ID = d.DIA_ID " +
                        "JOIN PLAN_ESTUDIO p ON c.PLAN_ESTUDIO_ID = p.PLAN_ESTUDIO_ID",
                resultSetMapping = "CursosMapping"
        ),
        @NamedNativeQuery(
                name = "Curso.findCursosByEstudiante",
                query = "SELECT c.CURSO_ID, c.NOMBRE, c.PLAN_ESTUDIO_ID, c.PROFESOR_ID, " +
                        "h.HORARIO_ID, d.DIA_ID, h.HORA_INICIO, h.HORA_FIN, h.UBICACION, " +
                        "d.NOMBRE as NOMBRE_DIA, p.NOMBRE as PLAN_NOMBRE, " +
                        "u.NOMBRE as PROFESOR_NOMBRE, u.APELLIDO as PROFESOR_APELLIDO " +
                        "FROM CURSO c " +
                        "JOIN HORARIO h ON c.HORARIO_ID = h.HORARIO_ID " +
                        "JOIN DIA_SEMANA d ON c.DIA_ID = d.DIA_ID " +
                        "JOIN PLAN_ESTUDIO p ON c.PLAN_ESTUDIO_ID = p.PLAN_ESTUDIO_ID " +
                        "JOIN USUARIO u ON c.PROFESOR_ID = u.USUARIO_ID " +
                        "JOIN MATRICULA m ON c.CURSO_ID = m.CURSO_ID " +
                        "WHERE m.ESTUDIANTE_ID = :estudianteId",
                resultSetMapping = "CursosEstudianteMapping"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "CursosMapping",
                columns = {
                        @ColumnResult(name = "CURSO_ID", type = Long.class),
                        @ColumnResult(name = "NOMBRE", type = String.class),
                        @ColumnResult(name = "PLAN_ESTUDIO_ID", type = Long.class),
                        @ColumnResult(name = "PROFESOR_ID", type = Long.class),
                        @ColumnResult(name = "HORARIO_ID", type = Long.class),
                        @ColumnResult(name = "DIA_ID", type = Long.class),
                        @ColumnResult(name = "HORA_INICIO", type = String.class),
                        @ColumnResult(name = "HORA_FIN", type = String.class),
                        @ColumnResult(name = "UBICACION", type = String.class),
                        @ColumnResult(name = "NOMBRE_DIA", type = String.class),
                        @ColumnResult(name = "PLAN_NOMBRE", type = String.class)
                }
        ),
        @SqlResultSetMapping(
                name = "CursosEstudianteMapping",
                columns = {
                        @ColumnResult(name = "CURSO_ID", type = Long.class),
                        @ColumnResult(name = "NOMBRE", type = String.class),
                        @ColumnResult(name = "PLAN_ESTUDIO_ID", type = Long.class),
                        @ColumnResult(name = "PROFESOR_ID", type = Long.class),
                        @ColumnResult(name = "HORARIO_ID", type = Long.class),
                        @ColumnResult(name = "DIA_ID", type = Long.class),
                        @ColumnResult(name = "HORA_INICIO", type = String.class),
                        @ColumnResult(name = "HORA_FIN", type = String.class),
                        @ColumnResult(name = "UBICACION", type = String.class),
                        @ColumnResult(name = "NOMBRE_DIA", type = String.class),
                        @ColumnResult(name = "PLAN_NOMBRE", type = String.class),
                        @ColumnResult(name = "PROFESOR_NOMBRE", type = String.class),
                        @ColumnResult(name = "PROFESOR_APELLIDO", type = String.class)
                }
        )
})
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