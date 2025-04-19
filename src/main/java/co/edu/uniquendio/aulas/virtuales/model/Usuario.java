package co.edu.uniquendio.aulas.virtuales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @Column(name = "USUARIO_ID")
    private Long usuarioId;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "CLAVE", nullable = false, length = 100)
    private String clave;

    @Column(name = "DOCUMENTO_ID", nullable = false, length = 20)
    private String documentoId;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "ROL_ID", nullable = false)
    private Rol rol;
}