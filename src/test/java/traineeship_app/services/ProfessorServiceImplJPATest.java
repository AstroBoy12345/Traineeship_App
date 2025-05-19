package traineeship_app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.EvaluationType;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.services.ProfessorServiceImpl;
import traineeship_app.mappers.ProfessorRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class ProfessorServiceImplJPATest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Autowired
    private ProfessorServiceImpl professorService;

    @Test
    void testSaveAndRetrieveProfile() {
        User proffessorUser = new User();
        proffessorUser.setUsername("prof1");
        proffessorUser.setPassword("1234");
        proffessorUser.setRole("Professor");
        proffessorUser.setProfileCompleted(true);

        Professor professor = new Professor();
        professor.setProfessorName("Prof. John Doe");
        professor.setInterests("AI, ML, Data Science");
        professor.setUser(proffessorUser); 

        professorService.saveProfile(professor);  // Ensure this method exists in ProfessorServiceImpl

        Professor retrieved = professorService.retrieveProfile("prof1");
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals("prof1", retrieved.getUsername());
        Assertions.assertEquals("Prof. John Doe", retrieved.getProfessorName());
    }

    @Test
    void testRetrieveAssignedPositions() {
        User proffessorUser = new User();
        proffessorUser.setUsername("prof2");
        proffessorUser.setPassword("1234");
        proffessorUser.setRole("Professor");
        proffessorUser.setProfileCompleted(true);

        Professor professor = new Professor();
        professor.setProfessorName("Prof. Jane Doe");
        professor.setInterests("AI, ML, Data Science");
        professor.setUser(proffessorUser); 

        professorRepository.save(professor);

        // Create and save traineeship positions
        TraineeshipPosition position1 = new TraineeshipPosition();
        position1.setAssigned(true);
        position1.setProfessor(professor);

        TraineeshipPosition position2 = new TraineeshipPosition();
        position2.setAssigned(false);
        position2.setProfessor(professor);

        traineeshipPositionRepository.save(position1);
        traineeshipPositionRepository.save(position2);

        // Retrieve assigned positions for this professor
        List<TraineeshipPosition> assignedPositions = professorService.retrieveAssignedPositions("prof2");

        Assertions.assertEquals(1, assignedPositions.size());
        Assertions.assertTrue(assignedPositions.get(0).isAssigned());
    }

    @Test
    void testEvaluateAssignedPosition() {
        User proffessorUser = new User();
        proffessorUser.setUsername("prof3");
        proffessorUser.setPassword("1234");
        proffessorUser.setRole("Professor");
        proffessorUser.setProfileCompleted(true);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        traineeshipPositionRepository.save(position);

        // Check if evaluation already exists
        boolean evaluationExists = professorService.evaluateAssignedPosition(position.getId());
        Assertions.assertFalse(evaluationExists); // No evaluations yet

        // Now add an evaluation
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVAL); // Assuming EvaluationType is an enum
        evaluation.setMotivation(5);
        evaluation.setEfficiency(4);
        evaluation.setEffectiveness(5);
        evaluation.setfacilitiesAndGuidance(4);
        professorService.saveEvaluation(position.getId(), evaluation); // Ensure this method exists in ProfessorServiceImpl

        // Check again if the evaluation already exists
        evaluationExists = professorService.evaluateAssignedPosition(position.getId());
        Assertions.assertTrue(evaluationExists); // Now we have an evaluation
    }

    @Test
    void testSaveEvaluation() {
        User proffessorUser = new User();
        proffessorUser.setUsername("prof1");
        proffessorUser.setPassword("1234");
        proffessorUser.setRole("Professor");
        proffessorUser.setProfileCompleted(true);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        traineeshipPositionRepository.save(position); // Save to ensure ID is set

        // Create evaluation and save it via service
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVAL);
        evaluation.setMotivation(4);
        evaluation.setEfficiency(3);
        evaluation.setEffectiveness(4);
        evaluation.setfacilitiesAndGuidance(3);
        professorService.saveEvaluation(position.getId(), evaluation);

        // Retrieve and assert
        Optional<TraineeshipPosition> retrieved = traineeshipPositionRepository.findById(position.getId());
        Assertions.assertTrue(retrieved.isPresent());

        List<Evaluation> evaluations = retrieved.get().getEvaluations();
        Assertions.assertEquals(1, evaluations.size());

        Evaluation e = evaluations.get(0);
        Assertions.assertEquals(EvaluationType.PROFESSOR_EVAL, e.getEvaluationType());
        Assertions.assertEquals(4, e.getMotivation());
        Assertions.assertEquals(3, e.getEfficiency());
        Assertions.assertEquals(4, e.getEffectiveness());
        Assertions.assertEquals(3, e.getFacilitiesAndGuidance());
    }

    @Test
    void testEvaluateAssignedPositionThrowsExceptionWhenPositionNotAssigned() {
        // Create and save a traineeship position that is not assigned
        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(false);
        traineeshipPositionRepository.save(position); // Save to ensure ID is set

        // Use assertThrows with a method reference or inline lambda (position not reassigned)
        Assertions.assertThrows(RuntimeException.class, () -> {
            professorService.evaluateAssignedPosition(position.getId());
        });
    }


    @Test
    void testSaveEvaluationThrowsExceptionWhenPositionNotFound() {
        // Trying to save an evaluation on a non-existent position
        Assertions.assertThrows(RuntimeException.class, () -> professorService.saveEvaluation(999, new Evaluation()));
    }
}
