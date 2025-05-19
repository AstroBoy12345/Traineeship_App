package traineeship_app.domainmodel;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class StudentTest {

    private TraineeshipPosition assignedTraineeship;

    @Test
    void testUserCreation() {
        Student student = new Student(); //every role works no need to test for different roles

        student.setUsername("testuser");
        student.setStudentName("John Doe");
        student.setAM("5142");
        student.setAvgGrade(7);
        student.setPreferredLocation("Alabama");
        student.setInterests("Basketball"); 
        student.setSkills("C Programming");
        student.setLookingForTraineeship(true);
        student.setAssignedTraineeship(assignedTraineeship);  

        assertEquals("testuser", student.getUsername());
        assertEquals("John Doe", student.getStudentName());
        assertEquals("5142", student.getAM());
        assertEquals(7, student.getAvgGrade());
        assertEquals("Alabama", student.getPreferredLocation());
        assertEquals("Basketball", student.getInterests());
        assertEquals("C Programming",student.getSkills());
        assertEquals(true, student.getLookingForTraineeship());
        assertEquals(assignedTraineeship, student.getAssignedTraineeship());
    }

    @Test
    void testGrantedAuthorities() {
        User user = new User();
        user.setRole("Student");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals(user.getRole().name(),authorities.iterator().next().getAuthority());
    }
}
