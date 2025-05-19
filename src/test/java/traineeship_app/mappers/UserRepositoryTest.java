package traineeship_app.mappers;

import traineeship_app.domainmodel.Role;
import traineeship_app.domainmodel.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testDatabaseType() {
        String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
        System.out.println("Connected to database: " + dbName);
    }

    @Test
    void testSaveAndFindUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("Company");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals(Role.USER_CO, foundUser.get().getRole());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("deletetest");
        user.setPassword("password");
        user.setRole("Company");

        userRepository.save(user);
        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findByUsername("deletetest");
        assertFalse(deletedUser.isPresent());
    }
}
