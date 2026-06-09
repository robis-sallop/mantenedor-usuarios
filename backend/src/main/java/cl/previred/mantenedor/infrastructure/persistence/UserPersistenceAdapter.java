package cl.previred.mantenedor.infrastructure.persistence;

import cl.previred.mantenedor.application.port.out.UserRepositoryPort;
import cl.previred.mantenedor.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador que conecta la capa de dominio con la persistencia JPA.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCorreoElectronico(String correoElectronico) {
        return jpaRepository.findByCorreoElectronico(correoElectronico).isPresent();
    }

    private User toDomain(UserEntity e) {
        return User.builder()
                .id(e.getId())
                .nombres(e.getNombres())
                .apellidos(e.getApellidos())
                .rut(e.getRut())
                .dv(e.getDv())
                .fechaNacimiento(e.getFechaNacimiento())
                .correoElectronico(e.getCorreoElectronico())
                .contrasena(e.getContrasena())
                .build();
    }

    private UserEntity toEntity(User u) {
        return UserEntity.builder()
                .id(u.getId())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .rut(u.getRut())
                .dv(u.getDv())
                .fechaNacimiento(u.getFechaNacimiento())
                .correoElectronico(u.getCorreoElectronico())
                .contrasena(u.getContrasena())
                .build();
    }
}
