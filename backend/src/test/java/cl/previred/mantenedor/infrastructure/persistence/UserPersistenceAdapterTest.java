package cl.previred.mantenedor.infrastructure.persistence;

import cl.previred.mantenedor.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
* UserPersistenceAdapterTest.
*
* @author  Roberto Salinas
* @version 1.0
*/
class UserPersistenceAdapterTest {

    private UserJpaRepository jpa;
    private UserPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        jpa = Mockito.mock(UserJpaRepository.class);
        adapter = new UserPersistenceAdapter(jpa);
    }

    @Test
    void save_mapsAndReturnsDomain() {
        User input = User.builder().nombres("X").correoElectronico("x@x.com").build();
        UserEntity saved = UserEntity.builder().id(10L).nombres("X").correoElectronico("x@x.com").build();

        when(jpa.save(any())).thenReturn(saved);

        User result = adapter.save(input);
        assertEquals(10L, result.getId());
        assertEquals("x@x.com", result.getCorreoElectronico());
    }

    @Test
    void findById_and_findAll_and_delete_and_exists() {
        UserEntity e1 = UserEntity.builder().id(1L).nombres("A").correoElectronico("a@a.com").build();
        UserEntity e2 = UserEntity.builder().id(2L).nombres("B").correoElectronico("b@b.com").build();

        when(jpa.findById(1L)).thenReturn(Optional.of(e1));
        when(jpa.findAll()).thenReturn(List.of(e1, e2));
        when(jpa.findByCorreoElectronico("a@a.com")).thenReturn(Optional.of(e1));

        Optional<User> opt = adapter.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals(1L, opt.get().getId());

        List<User> all = adapter.findAll();
        assertEquals(2, all.size());

        adapter.deleteById(1L);
        verify(jpa).deleteById(1L);

        assertTrue(adapter.existsByCorreoElectronico("a@a.com"));
        assertFalse(adapter.existsByCorreoElectronico("no@no.com"));
    }
}
