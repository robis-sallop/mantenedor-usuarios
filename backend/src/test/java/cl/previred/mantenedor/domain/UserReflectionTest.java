package cl.previred.mantenedor.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserReflectionTest {

    @Test
    void setters_getters_and_serialize() throws Exception {
        User u = new User();
        u.setId(77L);
        u.setNombres("Nu");
        u.setCorreoElectronico("nu@x.com");
        u.setFechaNacimiento(LocalDate.of(1995,5,5));

        assertEquals(77L, u.getId());
        assertEquals("Nu", u.getNombres());

        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
        String json = m.writeValueAsString(u);
        assertTrue(json.contains("nu@x.com"));

        User from = m.readValue(json, User.class);
        assertEquals(u.getNombres(), from.getNombres());
    }
}
