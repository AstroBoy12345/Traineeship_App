package traineeship_app.domainmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
class TraineeshipPositionTest {

    private TraineeshipPosition position;
    private Student student;
    private Professor professor;
    private Company company;
    private List<Evaluation> evaluations;

    @BeforeEach
    void setUp() {
        position = new TraineeshipPosition();
        student = new Student();
        professor = new Professor();
        company = new Company();
        evaluations = new ArrayList<>();
    }

    @Test
    void testTraineeshipPositionCreation() {
       
        position.setTitle("Software Development Internship");
        position.setDescription("Internship focused on Java and Spring Boot development.");
        position.setFromDate(LocalDate.of(2024, 6, 1));
        position.setToDate(LocalDate.of(2024, 12, 1));
        position.setTopics("Java, Spring Boot, REST APIs");
        position.setSkills("Java, SQL, Problem-solving");
        position.setAssigned(true);
        position.setStudentLogbook("logbook.pdf");
        position.setPassFailGrade(true);
        position.setStudent(student);
        position.setProfessor(professor);
        position.setCompany(company);
        position.setEvaluations(evaluations);

        assertEquals("Software Development Internship", position.getTitle());
        assertEquals("Internship focused on Java and Spring Boot development.", position.getDescription());
        assertEquals(LocalDate.of(2024, 6, 1), position.getFromDate());
        assertEquals(LocalDate.of(2024, 12, 1), position.getToDate());
        assertEquals("Java, Spring Boot, REST APIs", position.getTopics());
        assertEquals("Java, SQL, Problem-solving", position.getSkills());
        assertTrue(position.isAssigned());
        assertEquals("logbook.pdf", position.getStudentLogbook());
        assertTrue(position.getPassFailGrade());
        assertEquals(student, position.getStudent());
        assertEquals(professor, position.getProfessor());
        assertEquals(company, position.getCompany());
        assertEquals(evaluations, position.getEvaluations());
    }
}