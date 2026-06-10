package cl.previred.mantenedor.domain;

import cl.previred.mantenedor.infrastructure.persistence.UserEntity;
import cl.previred.mantenedor.web.dto.UserRequest;
import cl.previred.mantenedor.web.exception.ApiError;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ModelTests.
 *
 * @author  Roberto Salinas
 * @version 1.0
 */
class ModelTests {

    @Test
    void user_builder_and_equals() {
        User u1 = User.builder().id(20L).nombres("N").correoElectronico("n@x.com").build();
        User u2 = User.builder().id(20L).nombres("N").correoElectronico("n@x.com").build();

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertTrue(u1.toString().contains("N"));
    }

    @Test
    void userEntity_builder() {
        UserEntity e = UserEntity.builder().id(30L).nombres("E").correoElectronico("e@x.com").build();
        assertEquals(30L, e.getId());
        assertEquals("e@x.com", e.getCorreoElectronico());
    }

    @Test
    void userRequest_setters_getters() {
        UserRequest r = new UserRequest();
        r.setNombres("A");
        r.setApellidos("B");
        r.setRut(111L);
        r.setDv("1");
        r.setFechaNacimiento(LocalDate.of(2000,1,1));
        r.setCorreoElectronico("a@a.com");
        r.setContrasena("pw");

        assertEquals("A", r.getNombres());
        assertEquals("a@a.com", r.getCorreoElectronico());
    }

    @Test
    void apiError_record() {
        ApiError a = new ApiError(OffsetDateTime.now(), 418, "Err", "msg");
        assertEquals(418, a.status());
        assertEquals("msg", a.message());
    }
}
