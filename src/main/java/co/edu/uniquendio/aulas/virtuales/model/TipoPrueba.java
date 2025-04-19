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
@Table(name = "TIPO_PRUEBA")
public class TipoPrueba {

    @Id
    @Column(name = "TIPO_PRUEBA_ID")
    private Long tipoPruebaId;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "tipoPrueba")
    private List<Examen> examenes;
}