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
@Table(name = "HORARIO")
public class Horario {

    @Id
    @Column(name = "HORARIO_ID")
    private Long horarioId;

    @Column(name = "HORA_INICIO", length = 20)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 20)
    private String horaFin;

    @Column(name = "UBICACION", length = 100)
    private String ubicacion;

    @OneToMany(mappedBy = "horario")
    private List<Curso> cursos;
}