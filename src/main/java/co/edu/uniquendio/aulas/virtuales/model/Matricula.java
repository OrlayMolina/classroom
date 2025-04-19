package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MATRICULA")
@IdClass(MatriculaId.class)
public class Matricula {

    @Id
    @Column(name = "CURSO_ID")
    private Long cursoId;

    @Id
    @Column(name = "ESTUDIANTE_ID")
    private Long estudianteId;

    @Column(name = "FECHA_MATRICULA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMatricula;

    @ManyToOne
    @JoinColumn(name = "CURSO_ID", insertable = false, updatable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "ESTUDIANTE_ID", insertable = false, updatable = false)
    private Usuario estudiante;
}
