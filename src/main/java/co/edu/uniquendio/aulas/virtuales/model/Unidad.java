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
@Table(name = "UNIDAD")
public class Unidad {

    @Id
    @Column(name = "UNIDAD_ID")
    private Long unidadId;

    @Column(name = "NOMBRE_UNIDAD", nullable = false, length = 50)
    private String nombreUnidad;

    @OneToMany(mappedBy = "unidad")
    private List<Contenido> contenidos;
}