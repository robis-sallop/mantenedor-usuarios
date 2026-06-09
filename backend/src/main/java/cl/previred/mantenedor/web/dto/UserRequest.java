package cl.previred.mantenedor.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * DTO que representa la petición para crear o actualizar un usuario.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Data
@Schema(description = "Petición para crear o actualizar un usuario")
public class UserRequest {

    @NotBlank
    @Schema(description = "Nombres del usuario", example = "Juan José")
    private String nombres;

    @NotBlank
    @Schema(description = "Apellidos del usuario", example = "Pérez Díaz")
    private String apellidos;

    @NotNull
    @Schema(description = "RUT (sin dígito verificador)", example = "12345678")
    private Long rut;

    @NotBlank
    @Schema(description = "Dígito verificador del RUT", example = "9")
    private String dv;

    @NotNull
    @Schema(description = "Fecha de nacimiento", example = "1990-01-01")
    private LocalDate fechaNacimiento;

    @NotBlank
    @Email
    @Schema(description = "Correo electrónico", example = "juan@example.com")
    private String correoElectronico;

    @NotBlank
    @Schema(description = "Contraseña", example = "P@ssw0rd")
    private String contrasena;
}
