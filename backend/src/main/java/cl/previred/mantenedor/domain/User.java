package cl.previred.mantenedor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Entidad de dominio que representa un usuario dentro de la aplicación.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad de dominio Usuario")
public class User {
    @Schema(description = "Identificador", example = "1")
    private Long id;

    @Schema(description = "Nombres", example = "Juan José")
    private String nombres;

    @Schema(description = "Apellidos", example = "Pérez Díaz")
    private String apellidos;

    @Schema(description = "RUT (sin dígito verificador)", example = "12345678")
    private Long rut;

    @Schema(description = "Dígito verificador del RUT", example = "9")
    private String dv;

    @Schema(description = "Fecha de nacimiento", example = "1990-01-01")
    private LocalDate fechaNacimiento;

    @Schema(description = "Correo electrónico", example = "juan@example.com")
    private String correoElectronico;

    @Schema(description = "Contraseña (no se devuelve en respuestas)", example = "P@ssw0rd")
    private String contrasena;
}
