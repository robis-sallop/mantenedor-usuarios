package cl.previred.mantenedor.web.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de respuesta que representa un usuario retornado por la API.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Schema(description = "Respuesta con los datos de un usuario")
public record UserResponse(
        @Schema(description = "Identificador", example = "1") Long id,
        @Schema(description = "Nombres", example = "Juan José") String nombres,
        @Schema(description = "Apellidos", example = "Pérez Díaz") String apellidos,
        @Schema(description = "RUT", example = "12345678") Long rut,
        @Schema(description = "Dígito verificador", example = "9") String dv,
        @Schema(description = "Fecha de nacimiento", example = "1990-01-01") LocalDate fechaNacimiento,
        @Schema(description = "Correo electrónico", example = "juan@example.com") String correoElectronico
) {
}
