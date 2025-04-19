package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ENVIO_EXAMEN")
public class EnvioExamen {

    @Id
    @Column(name = "ENVIO_ID")
    private Long envioId;

    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "PUNTAJE", precision = 5, scale = 2)
    private BigDecimal puntaje;

    @Column(name = "IP", length = 50)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "EXAMEN_ID")
    private Examen examen;

    @ManyToOne
    @JoinColumn(name = "ESTUDIANTE_ID")
    private Usuario estudiante;

    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas;
}
