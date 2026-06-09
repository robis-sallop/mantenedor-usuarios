package cl.previred.mantenedor.application.service;

import cl.previred.mantenedor.application.port.out.UserRepositoryPort;
import cl.previred.mantenedor.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test unitario para UserService.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
class UserServiceTest {

    private UserRepositoryPort repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(UserRepositoryPort.class);
        service = new UserService(repository);
    }

    @Test
    void createUser_success() {
        User u = User.builder()
                .nombres("Juan")
                .apellidos("Perez")
                .rut(12345678L)
                .dv("9")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correoElectronico("juan@example.com")
                .contrasena("secret")
                .build();

        when(repository.existsByCorreoElectronico(u.getCorreoElectronico())).thenReturn(false);
        when(repository.save(any())).thenAnswer(i -> {
            User arg = i.getArgument(0);
            return User.builder()
                    .id(1L)
                    .nombres(arg.getNombres())
                    .apellidos(arg.getApellidos())
                    .rut(arg.getRut())
                    .dv(arg.getDv())
                    .fechaNacimiento(arg.getFechaNacimiento())
                    .correoElectronico(arg.getCorreoElectronico())
                    .contrasena(arg.getContrasena())
                    .build();
        });

        User created = service.createUser(u);
        assertNotNull(created.getId());
        assertEquals("juan@example.com", created.getCorreoElectronico());
    }

    @Test
    void createUser_conflict() {
        User u = User.builder()
                .correoElectronico("dup@example.com")
                .build();

        when(repository.existsByCorreoElectronico(u.getCorreoElectronico())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> service.createUser(u));
    }

    @Test
    void getUser_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        Optional<User> u = service.getUser(99L);
        assertTrue(u.isEmpty());
    }

    @Test
    void updateUser_success() {
        User existing = User.builder()
                .id(1L)
                .nombres("Juan")
                .apellidos("Perez")
                .rut(12345678L)
                .dv("9")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correoElectronico("juan@example.com")
                .contrasena("secret")
                .build();

        User updateData = User.builder()
                .nombres("Juan Carlos")
                .apellidos("Perez Lopez")
                .rut(12345678L)
                .dv("9")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correoElectronico("juancarlos@example.com")
                .contrasena("newsecret")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        User updated = service.updateUser(1L, updateData);
        assertEquals("Juan Carlos", updated.getNombres());
        assertEquals("juancarlos@example.com", updated.getCorreoElectronico());
        assertEquals(1L, updated.getId());
    }

    @Test
    void updateUser_notFound() {
        User u = User.builder()
                .nombres("Juan")
                .apellidos("Perez")
                .build();

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.updateUser(99L, u));
    }

    @Test
    void deleteUser_success() {
        User existing = User.builder()
                .id(1L)
                .nombres("Juan")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        assertDoesNotThrow(() -> service.deleteUser(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.deleteUser(99L));
    }
}
