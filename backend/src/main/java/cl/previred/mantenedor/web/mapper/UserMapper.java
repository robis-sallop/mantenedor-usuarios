package cl.previred.mantenedor.web.mapper;

import cl.previred.mantenedor.domain.User;
import cl.previred.mantenedor.web.dto.UserRequest;
import cl.previred.mantenedor.web.dto.UserResponse;
import org.springframework.stereotype.Component;

/**
 * Convertidor entre DTOs de la capa web y la entidad de dominio User.
 *
 * @author Roberto Salinas
 * @version 1.0
 *
 */
@Component
public class UserMapper {
    /**
     * Convierte un UserRequest a la entidad de dominio User.
     */
    public User toDomain(UserRequest r) {
        return User.builder()
                .nombres(r.getNombres())
                .apellidos(r.getApellidos())
                .rut(r.getRut())
                .dv(r.getDv())
                .fechaNacimiento(r.getFechaNacimiento())
                .correoElectronico(r.getCorreoElectronico())
                .contrasena(r.getContrasena())
                .build();
    }

    /**
     * Convierte la entidad de dominio User a UserResponse.
     */
    public UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getNombres(),
                u.getApellidos(),
                u.getRut(),
                u.getDv(),
                u.getFechaNacimiento(),
                u.getCorreoElectronico()
        );
    }
}
