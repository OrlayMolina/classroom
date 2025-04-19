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
@Table(name = "CONTENIDO")
public class Contenido {

    @Id
    @Column(name = "CONTENIDO_ID")
    private Long contenidoId;

    @Column(name = "DESCRIPCION_CONTENIDO", length = 4000)
    private String descripcionContenido;

    @ManyToOne
    @JoinColumn(name = "UNIDAD_ID")
    private Unidad unidad;

    @ManyToMany(mappedBy = "contenidos")
    private List<PlanEstudio> planesEstudio;
}