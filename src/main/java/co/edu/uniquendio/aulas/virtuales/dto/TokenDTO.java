package co.edu.uniquendio.aulas.virtuales.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    @NotBlank(message = "El token de autenticación es obligatorio")
    private String token;
}
