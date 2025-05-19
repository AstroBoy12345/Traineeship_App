package traineeship_app.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import traineeship_app.domainmodel.TraineeshipPosition;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TraineeshipPositionRepositoryTest {

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @BeforeEach
    void setUp() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setTitle("Software Engineer Intern");
        traineeshipPositionRepository.save(position);
    }

    @Test
    void testFindByTitle_ShouldReturnTraineeshipPosition() {
        TraineeshipPosition found = traineeshipPositionRepository.findByTitle("Software Engineer Intern");

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Software Engineer Intern");
    }

    @Test
    void testFindByTitle_ShouldReturnNull_WhenNotFound() {
        TraineeshipPosition found = traineeshipPositionRepository.findByTitle("Data Scientist Intern");

        assertThat(found).isNull();
    }
}
