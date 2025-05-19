package traineeship_app.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import traineeship_app.domainmodel.User;
import traineeship_app.mappers.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class UserServiceImplJpaTest {

    @Autowired
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        // Inject the real repository manually (field is private — constructor preferred in real design)
        // Use reflection workaround or redesign UserServiceImpl for constructor injection in production
        try {
            var field = UserServiceImpl.class.getDeclaredField("userRepository");
            field.setAccessible(true);
            field.set(userService, userRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSaveUserAndFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("secret");

        userService.saveUser(user);

        Optional<User> found = userService.findByUsername("testuser");
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }

    @Test
    void testIsUserPresent() {
        User user = new User();
        user.setUsername("jane");
        user.setPassword("12345");

        userService.saveUser(user);

        assertTrue(userService.isUserPresent("jane"));
        assertFalse(userService.isUserPresent("ghost"));
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("pass");

        userService.saveUser(user);

        var loaded = userService.loadUserByUsername("alice");
        assertEquals("alice", loaded.getUsername());
    }

    @Test
    void testLoadUserByUsernameThrows() {
        assertThrows(
            org.springframework.security.core.userdetails.UsernameNotFoundException.class,
            () -> userService.loadUserByUsername("notfound")
        );
    }
}

