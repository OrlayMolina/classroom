package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROL")
public class Rol {

    @Id
    @Column(name = "ROL_ID")
    private Long rolId;

    @Column(name = "NOMBRE_ROL", nullable = false, length = 20)
    private String nombreRol;
}