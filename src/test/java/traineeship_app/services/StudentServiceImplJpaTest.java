package traineeship_app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.mappers.StudentRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;
import traineeship_app.mappers.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class StudentServiceImplJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Autowired
    private StudentServiceImpl studentService;

    @Test
    void testSaveAndRetrieveStudentProfile() {
        User studentUser = new User();
        studentUser.setUsername("student1");
        studentUser.setPassword("1234");
        studentUser.setRole("Student");
        studentUser.setProfileCompleted(true);

        Student student = new Student();
        student.setStudentName("Jane Doe");
        student.setAM("123456");
        student.setAvgGrade(8.5);
        student.setPreferredLocation("Athens");
        student.setInterests("Software Development");
        student.setSkills("Java, Spring Boot");
        student.setLookingForTraineeship(true);
        student.setUser(studentUser);

        studentService.saveProfile(student);

        Student retrieved = studentService.retrieveProfile("student1");
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals("student1", retrieved.getUsername());
        Assertions.assertEquals("Jane Doe", retrieved.getStudentName());
        Assertions.assertEquals("123456", retrieved.getAM());
        Assertions.assertEquals(8.5, retrieved.getAvgGrade());
        Assertions.assertEquals("Athens", retrieved.getPreferredLocation());
        Assertions.assertEquals("Software Development", retrieved.getInterests());
        Assertions.assertEquals("Java, Spring Boot", retrieved.getSkills());
        Assertions.assertTrue(retrieved.getLookingForTraineeship());
    }

    @Test
    void testRetrieveProfileNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            studentService.retrieveProfile("unknownUser");
        });
    }

    @Test
    void testSaveLogBookSuccess() {
        // Set up student and traineeship position
        User studentUser = new User();
        studentUser.setUsername("student2");
        studentUser.setPassword("1234");
        studentUser.setRole("Student");
        studentUser.setProfileCompleted(true);

        Student student = new Student();
        student.setStudentName("John Smith");
        student.setUser(studentUser);
        studentRepository.save(student);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        position.setStudent(student);
        position.setStudentLogbook("Initial Log");
        traineeshipPositionRepository.save(position);

        // Modify logbook and save through service
        position.setStudentLogbook("Updated Logbook Entry");
        studentService.saveLogBook(position);

        TraineeshipPosition updated = traineeshipPositionRepository.findById(position.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals("Updated Logbook Entry", updated.getStudentLogbook());
    }

    @Test
    void testSaveLogBookNotFound() {
        TraineeshipPosition position = new TraineeshipPosition();

        assertThrows(RuntimeException.class, () -> {
            studentService.saveLogBook(position); 
        });
    }
}