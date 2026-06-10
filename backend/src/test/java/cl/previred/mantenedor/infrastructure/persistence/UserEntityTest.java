package cl.previred.mantenedor.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
 * UserEntityTest.
 *
 * @author  Roberto Salinas
 * @version 1.0
 */
class UserEntityTest {

    @Test
    void builder_and_getters() {
        UserEntity e = UserEntity.builder()
                .id(99L)
                .nombres("Ent")
                .apellidos("Last")
                .rut(123L)
                .dv("9")
                .fechaNacimiento(LocalDate.of(1999,9,9))
                .correoElectronico("ent@x.com")
                .contrasena("pw")
                .build();

        assertEquals(99L, e.getId());
        assertEquals("Ent", e.getNombres());
        assertEquals("ent@x.com", e.getCorreoElectronico());
        assertTrue(e.toString().contains("Ent"));
    }
}
