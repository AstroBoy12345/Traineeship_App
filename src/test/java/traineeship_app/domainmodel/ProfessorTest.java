package traineeship_app.domainmodel;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
class ProfessorTest {

    private List<TraineeshipPosition> traineeshipPositions;

    @Test
    void testUserCreation() {
        Professor professor = new Professor(); //every role works no need to test for different roles
        traineeshipPositions = new ArrayList<>();

        professor.setUsername("testuser");
        professor.setProfessorName("John Doe");
        professor.setInterests("Basketball"); 
        professor.setSupervisedPositions(traineeshipPositions);  

        assertEquals("testuser", professor.getUsername());
        assertEquals("John Doe", professor.getProfessorName());
        assertEquals("Basketball", professor.getInterests());
        assertEquals(traineeshipPositions, professor.getSupervisedPositions());
    }
}
