package traineeship_app.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {        
        Student student = new Student();
        student.setUsername("test_student");
        student.setAM("5389");
        student.setStudentName("Nektaria");
        
        TraineeshipPosition traineeship = new TraineeshipPosition();
        traineeship.setTitle("Software Engineer Intern");
        traineeship.setStudent(student);
        student.setAssignedTraineeship(traineeship);

        studentRepository.save(student);
    }

    @Test
    void testFindByUsername_ShouldReturnStudent() {
        Optional<Student> found = studentRepository.findByUsername("test_student");
        
        assertThat(found).isPresent(); // ✅ Ελέγχει ότι υπάρχει Student
        Student student = found.get(); // ✅ Παίρνεις το Student από το Optional


        assertThat(student.getUsername()).isEqualTo("test_student");
        assertThat(student.getStudentName()).isEqualTo("Nektaria");
        assertThat(student.getAM()).isEqualTo("5389");
        assertThat(student.getAssignedTraineeship()).isNotNull();
        assertThat(student.getAssignedTraineeship().getTitle()).isEqualTo("Software Engineer Intern");
    }

    @Test
    void testFindByUsername_ShouldReturnEmpty_WhenNotFound() {
        Optional<Student> found = studentRepository.findByUsername("unknown_user");

        assertThat(found).isEmpty(); // ✅ Το Optional είναι empty (δηλαδή δεν υπάρχει Student)
    }
}
