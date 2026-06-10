package cl.previred.mantenedor.web.controller;

import cl.previred.mantenedor.application.port.in.UserUseCase;
import cl.previred.mantenedor.domain.User;
import cl.previred.mantenedor.web.dto.UserRequest;
import cl.previred.mantenedor.web.dto.UserResponse;
import cl.previred.mantenedor.web.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserUseCase userUseCase;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void list_ReturnsOk() throws Exception {
        User u = User.builder().id(1L).nombres("Juan").apellidos("Perez").rut(123L).dv("9")
                .fechaNacimiento(LocalDate.of(1990,1,1)).correoElectronico("a@b.com").build();
        when(userUseCase.listUsers()).thenReturn(Collections.singletonList(u));
        when(mapper.toResponse(any())).thenReturn(new UserResponse(1L, "Juan","Perez",123L,"9",LocalDate.of(1990,1,1),"a@b.com"));

        mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void get_WhenNotFound_Returns404() throws Exception {
        when(userUseCase.getUser(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99")).andExpect(status().isNotFound());
    }

    @Test
    void get_WhenFound_ReturnsOk() throws Exception {
        User u = User.builder().id(4L).nombres("Luis").apellidos("Diaz").rut(333L).dv("3")
                .fechaNacimiento(LocalDate.of(1985,5,6)).correoElectronico("luis@x.com").build();

        when(userUseCase.getUser(4L)).thenReturn(Optional.of(u));
        when(mapper.toResponse(any())).thenReturn(new UserResponse(4L, "Luis","Diaz",333L,"3",LocalDate.of(1985,5,6),"luis@x.com"));

        mockMvc.perform(get("/api/users/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.correoElectronico").value("luis@x.com"));
    }

    @Test
    void create_ReturnsCreated() throws Exception {
        UserRequest req = new UserRequest();
        req.setNombres("Ana");
        req.setApellidos("Lopez");
        req.setRut(111L);
        req.setDv("1");
        req.setFechaNacimiento(LocalDate.of(1995,2,3));
        req.setCorreoElectronico("ana@x.com");
        req.setContrasena("pw");

        User domain = User.builder().id(2L).nombres("Ana").apellidos("Lopez").rut(111L).dv("1")
                .fechaNacimiento(LocalDate.of(1995,2,3)).correoElectronico("ana@x.com").contrasena("pw").build();

        when(mapper.toDomain(any())).thenReturn(domain);
        when(userUseCase.createUser(any())).thenReturn(domain);
        when(mapper.toResponse(any())).thenReturn(new UserResponse(2L, "Ana","Lopez",111L,"1",LocalDate.of(1995,2,3),"ana@x.com"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void delete_ReturnsNoContent() throws Exception {
        Mockito.doNothing().when(userUseCase).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());
    }

        @Test
        void update_ReturnsOk() throws Exception {
        UserRequest req = new UserRequest();
        req.setNombres("Carlos");
        req.setApellidos("Gonzalez");
        req.setRut(222L);
        req.setDv("2");
        req.setFechaNacimiento(LocalDate.of(1988,3,4));
        req.setCorreoElectronico("carlos@x.com");
        req.setContrasena("pw2");

        User domain = User.builder().nombres("Carlos").apellidos("Gonzalez").rut(222L).dv("2")
            .fechaNacimiento(LocalDate.of(1988,3,4)).correoElectronico("carlos@x.com").contrasena("pw2").build();

        User updatedDomain = User.builder().id(3L).nombres("Carlos").apellidos("Gonzalez").rut(222L).dv("2")
            .fechaNacimiento(LocalDate.of(1988,3,4)).correoElectronico("carlos@x.com").contrasena("pw2").build();

        when(mapper.toDomain(any())).thenReturn(domain);
        when(userUseCase.updateUser(eq(3L), any())).thenReturn(updatedDomain);
        when(mapper.toResponse(any())).thenReturn(new UserResponse(3L, "Carlos","Gonzalez",222L,"2",LocalDate.of(1988,3,4),"carlos@x.com"));

        mockMvc.perform(put("/api/users/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.correoElectronico").value("carlos@x.com"));

        Mockito.verify(userUseCase).updateUser(eq(3L), any());
        }
}
