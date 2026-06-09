package cl.previred.mantenedor.web.exception;

import java.time.OffsetDateTime;

/**
 * ApiError es un DTO que representa la estructura de un error en la API.
 * Contiene información sobre el error, como la fecha y hora, el código de estado HTTP,
 * el mensaje de error y una descripción detallada del error.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
public record ApiError(OffsetDateTime timestamp, int status, String error, String message) {
}
