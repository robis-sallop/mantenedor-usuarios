package cl.previred.mantenedor.application.service;

import cl.previred.mantenedor.application.port.in.UserUseCase;
import cl.previred.mantenedor.application.port.out.UserRepositoryPort;
import cl.previred.mantenedor.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la lógica de negocio para la gestión de usuarios.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort repository;

    /**
     * Crea un nuevo usuario. Valida correo y unicidad.
     *
     * @param user usuario a crear
     * @return usuario creado con id
     * @throws IllegalArgumentException si faltan datos requeridos
     * @throws DataIntegrityViolationException si el correo ya existe
     */
    @Override
    public User createUser(User user) {
        if (user.getCorreoElectronico() == null) {
            throw new IllegalArgumentException("correoElectronico es requerido");
        }
        if (repository.existsByCorreoElectronico(user.getCorreoElectronico())) {
            throw new DataIntegrityViolationException("Correo ya registrado");
        }
        User userToSave = User.builder()
                .nombres(user.getNombres())
                .apellidos(user.getApellidos())
                .rut(user.getRut())
                .dv(user.getDv())
                .fechaNacimiento(user.getFechaNacimiento())
                .correoElectronico(user.getCorreoElectronico())
                .contrasena(user.getContrasena())
                .build();
        return repository.save(userToSave);
    }

    /**
     * Obtiene un usuario por id.
     */
    @Override
    public Optional<User> getUser(Long id) {
        return repository.findById(id);
    }

    /**
     * Lista todos los usuarios.
     */
    @Override
    public List<User> listUsers() {
        return repository.findAll();
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id   id del usuario a actualizar
     * @param user datos con los cambios
     * @return usuario actualizado
     * @throws IllegalArgumentException si el usuario no existe
     */
    @Override
    public User updateUser(Long id, User user) {
        Optional<User> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        User e = existing.get();
        User updatedUser = User.builder()
                .id(e.getId())
                .nombres(user.getNombres())
                .apellidos(user.getApellidos())
                .rut(user.getRut())
                .dv(user.getDv())
                .fechaNacimiento(user.getFechaNacimiento())
                .correoElectronico(user.getCorreoElectronico())
                .contrasena(user.getContrasena())
                .build();
        return repository.save(updatedUser);
    }

    /**
     * Elimina un usuario por id.
     *
     * @param id id del usuario a eliminar
     * @throws IllegalArgumentException si el usuario no existe
     */
    @Override
    public void deleteUser(Long id) {
        Optional<User> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        repository.deleteById(id);
    }
}
