package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONFIGURACION_EXAMEN")
public class ConfiguracionExamen {

    @Id
    @Column(name = "CONFIG_ID")
    private Long configId;

    @Column(name = "PESO", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "UMBRAL", precision = 5, scale = 2)
    private BigDecimal umbral;

    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    @Column(name = "CANT_PREGUNTAS")
    private Integer cantPreguntas;

    @Column(name = "MODO_SELECCION", length = 20)
    private String modoSeleccion;

    @Column(name = "LIMITE_TIEMPO")
    private Integer limiteTiempo;

    @OneToOne
    @JoinColumn(name = "EXAMEN_ID", unique = true)
    private Examen examen;
}