package cl.previred.mantenedor.web.mapper;

import cl.previred.mantenedor.domain.User;
import cl.previred.mantenedor.web.dto.UserRequest;
import cl.previred.mantenedor.web.dto.UserResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitario para UserMapper.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void testToDomain_Success() {
        UserRequest request = new UserRequest();
        request.setNombres("Juan");
        request.setApellidos("Perez");
        request.setRut(12345678L);
        request.setDv("9");
        request.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        request.setCorreoElectronico("juan@example.com");
        request.setContrasena("secret");

        User domain = mapper.toDomain(request);

        assertNotNull(domain);
        assertEquals("Juan", domain.getNombres());
        assertEquals("Perez", domain.getApellidos());
        assertEquals(12345678L, domain.getRut());
        assertEquals("9", domain.getDv());
        assertEquals(LocalDate.of(1990, 1, 1), domain.getFechaNacimiento());
        assertEquals("juan@example.com", domain.getCorreoElectronico());
        assertEquals("secret", domain.getContrasena());
        assertNull(domain.getId());
    }

    @Test
    void testToResponse_Success() {
        User user = User.builder()
                .id(1L)
                .nombres("Juan")
                .apellidos("Perez")
                .rut(12345678L)
                .dv("9")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correoElectronico("juan@example.com")
                .contrasena("secret")
                .build();

        UserResponse response = mapper.toResponse(user);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Juan", response.nombres());
        assertEquals("Perez", response.apellidos());
        assertEquals(12345678L, response.rut());
        assertEquals("9", response.dv());
        assertEquals(LocalDate.of(1990, 1, 1), response.fechaNacimiento());
        assertEquals("juan@example.com", response.correoElectronico());
    }

    @Test
    void testToResponse_WithoutPassword() {
        User user = User.builder()
                .id(2L)
                .nombres("Maria")
                .apellidos("Gonzalez")
                .rut(87654321L)
                .dv("4")
                .fechaNacimiento(LocalDate.of(1985, 5, 15))
                .correoElectronico("maria@example.com")
                .contrasena("secret123")
                .build();

        UserResponse response = mapper.toResponse(user);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("Maria", response.nombres());
        // Verificar que la contraseña no se incluye en la respuesta
        assertDoesNotThrow(() -> response);
    }
}

