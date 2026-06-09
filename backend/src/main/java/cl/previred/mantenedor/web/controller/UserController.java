package cl.previred.mantenedor.web.controller;

import cl.previred.mantenedor.application.port.in.UserUseCase;
import cl.previred.mantenedor.domain.User;
import cl.previred.mantenedor.web.dto.UserRequest;
import cl.previred.mantenedor.web.dto.UserResponse;
import cl.previred.mantenedor.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operaciones CRUD sobre usuarios.
 *
 * @author Roberto Salinas
 * @version 1.0
 * */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserMapper mapper;

    public UserController(UserUseCase userUseCase, UserMapper mapper) {
        this.userUseCase = userUseCase;
        this.mapper = mapper;
    }

    /**
     * Obtiene la lista de usuarios.
     *
     * @return lista de usuarios
     */
    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna la lista de todos los usuarios registrados")
    public ResponseEntity<List<UserResponse>> list() {
        List<UserResponse> list = userUseCase.listUsers().stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /**
     * Obtiene un usuario por su id.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Busca un usuario por su ID")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return userUseCase.getUser(id).map(u -> ResponseEntity.ok(mapper.toResponse(u))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param request datos del usuario a crear
     * @return usuario creado
     */
    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        User u = mapper.toDomain(request);
        User created = userUseCase.createUser(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id      identificador del usuario a actualizar
     * @param request datos actualizados
     * @return usuario actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        User u = mapper.toDomain(request);
        User updated = userUseCase.updateUser(id, u);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    /**
     * Elimina un usuario.
     *
     * @param id identificador del usuario a eliminar
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
