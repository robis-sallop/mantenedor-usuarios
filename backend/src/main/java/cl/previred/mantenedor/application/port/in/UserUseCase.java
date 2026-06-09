package cl.previred.mantenedor.application.port.in;

import cl.previred.mantenedor.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * UserUseCase.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
public interface UserUseCase {
    User createUser(User user);
    Optional<User> getUser(Long id);
    List<User> listUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}

