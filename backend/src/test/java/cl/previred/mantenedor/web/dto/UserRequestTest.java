package cl.previred.mantenedor.web.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestTest {

    @Test
    void settersAndGetters() {
        UserRequest r = new UserRequest();
        r.setNombres("A");
        r.setApellidos("B");
        r.setRut(111L);
        r.setDv("1");
        r.setFechaNacimiento(LocalDate.of(2000,1,1));
        r.setCorreoElectronico("a@a.com");
        r.setContrasena("pw");

        assertEquals("A", r.getNombres());
        assertEquals("B", r.getApellidos());
        assertEquals(111L, r.getRut());
        assertEquals("1", r.getDv());
        assertEquals(LocalDate.of(2000,1,1), r.getFechaNacimiento());
        assertEquals("a@a.com", r.getCorreoElectronico());
        assertEquals("pw", r.getContrasena());
    }
}
