package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long usuarioId;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 50, message = "El email no puede superar los 50 caracteres")
    private String email;

    @NotBlank(message = "La clave es obligatoria")
    @Size(max = 100, message = "La clave no puede superar los 100 caracteres")
    private String clave;

    @NotBlank(message = "El documento de identidad es obligatorio")
    @Size(max = 20, message = "El documento no puede superar los 20 caracteres")
    private String documentoId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @NotNull(message = "El ID del rol es obligatorio")
    private Long rolId;

    // Campos adicionales para mostrar información relacionada
    private String nombreRol;
}