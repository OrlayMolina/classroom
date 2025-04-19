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
@Table(name = "DIAS_SEMANA")
public class DiaSemana {

    @Id
    @Column(name = "DIA_ID")
    private Long diaId;

    @Column(name = "NOMBRE_DIA", nullable = false, length = 20)
    private String nombreDia;

    @OneToMany(mappedBy = "dia")
    private List<Curso> cursos;
}
