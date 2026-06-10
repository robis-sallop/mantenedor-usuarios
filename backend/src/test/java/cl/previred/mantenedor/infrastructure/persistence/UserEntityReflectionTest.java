package cl.previred.mantenedor.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityReflectionTest {

    @Test
    void instantiateWithProtectedConstructor_and_setters() throws Exception {
        Constructor<UserEntity> ctor = UserEntity.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        UserEntity e = ctor.newInstance();

        e.setId(55L);
        e.setNombres("Ref");
        e.setApellidos("A");
        e.setRut(123L);
        e.setDv("1");
        e.setFechaNacimiento(LocalDate.of(2001,1,1));
        e.setCorreoElectronico("r@r.com");
        e.setContrasena("pw");

        assertEquals(55L, e.getId());
        assertEquals("r@r.com", e.getCorreoElectronico());
        assertTrue(e.toString().contains("Ref"));
    }
}
