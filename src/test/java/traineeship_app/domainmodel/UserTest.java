package traineeship_app.domainmodel;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UserTest {

    @Test
    void testUserCreation() {
        User user = new User(); //every role works no need to test for different roles

        user.setUsername("testuser");
        user.setPassword("securepassword");
        user.setRole("Professor");

        assertEquals("testuser", user.getUsername());
        assertEquals("securepassword", user.getPassword());
        assertEquals(Role.USER_PR, user.getRole());
    }

    @Test
    void testGrantedAuthorities() {
        User user = new User();
        user.setRole("Professor");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals(user.getRole().name(),authorities.iterator().next().getAuthority());
    }

}