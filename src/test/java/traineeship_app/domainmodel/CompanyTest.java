package traineeship_app.domainmodel;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
class CompanyTest {

    private List<TraineeshipPosition> positions;

    @Test
    void testUserCreation() {
        Company company = new Company(); //every role works no need to test for different roles
        positions = new ArrayList<>();

        company.setUsername("testuser");
        company.setCompanyName("Team Viewer");
        company.setCompanyLocation("Kazakhstan"); 
        company.setPositions(positions);  

        assertEquals("testuser", company.getUsername());
        assertEquals("Team Viewer", company.getCompanyName());
        assertEquals("Kazakhstan", company.getCompanyLocation());
        assertEquals(positions, company.getPositions());
    }

    @Test
    void testGrantedAuthorities() {
        User user = new User();
        user.setRole("Company");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals(user.getRole().name(),authorities.iterator().next().getAuthority());
    }
}
