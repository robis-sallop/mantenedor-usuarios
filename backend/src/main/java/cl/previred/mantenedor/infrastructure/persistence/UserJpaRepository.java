package cl.previred.mantenedor.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserJpaRepository es la interfaz de acceso a datos para la entidad UserEntity.
 * Extiende JpaRepository para proporcionar métodos CRUD y define consultas personalizadas.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByCorreoElectronico(String correoElectronico);
}

