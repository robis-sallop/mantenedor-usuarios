package cl.previred.mantenedor.web.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestSerializationTest {

    @Test
    void serializeDeserialize() throws Exception {
        UserRequest r = new UserRequest();
        r.setNombres("S");
        r.setApellidos("T");
        r.setRut(11L);
        r.setDv("1");
        r.setFechaNacimiento(LocalDate.of(1990,1,1));
        r.setCorreoElectronico("s@t.com");
        r.setContrasena("pw");

        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
        String json = m.writeValueAsString(r);
        assertTrue(json.contains("s@t.com"));

        UserRequest r2 = m.readValue(json, UserRequest.class);
        assertEquals("S", r2.getNombres());
    }
}
