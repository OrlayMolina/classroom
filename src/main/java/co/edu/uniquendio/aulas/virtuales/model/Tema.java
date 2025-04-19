package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEMA")
public class Tema {

    @Id
    @Column(name = "TEMA_ID")
    private Long temaId;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
}