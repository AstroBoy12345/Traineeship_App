package traineeship_app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import org.springframework.transaction.annotation.Transactional;
import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.EvaluationType;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.mappers.CompanyRepository;
import traineeship_app.mappers.EvaluationRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class CompanyServiceImplJpaTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CompanyServiceImpl companyService;

    @Test
    void testSaveAndRetrieveProfile() {
        
        User companyUser = new User();
        companyUser.setUsername("comp1");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);
        
        // Create and save company
        Company company = new Company();
        company.setCompanyName("TechCorp");
        company.setCompanyLocation("New York");
        company.setUser(companyUser);
        
        companyService.saveProfile(company);

        // Retrieve the company and check the data
        Company retrieved = companyService.retrieveProfile("comp1");
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals("comp1", retrieved.getUsername());
        Assertions.assertEquals("TechCorp", retrieved.getCompanyName());
    }

    @Test
    void testAddAndRetrievePositions() {
       
        User companyUser = new User();
        companyUser.setUsername("comp2");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);
        
        // Create and save company
        Company company = new Company();
        company.setCompanyName("InnovateLtd");
        company.setUser(companyUser);
        
        companyService.saveProfile(company);

        // Add new traineeship position
        TraineeshipPosition position = new TraineeshipPosition();
        position.setTitle("Software Intern");        
        position.setAssigned(true);
        
        companyService.addPosition("comp2", position);

        // Retrieve the position and check
        List<TraineeshipPosition> positions = companyService.retrieveAvailablePositions("comp2");
        assertEquals(1, positions.size());
        assertEquals("Software Intern", positions.get(0).getTitle());
        assertFalse(positions.get(0).isAssigned());
    }

    @Test
    void testSaveEvaluation() {
        
        User companyUser = new User();
        companyUser.setUsername("comp3");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);
        
        // Create and save company and traineeship position
        Company company = new Company();
        company.setCompanyName("CodeWorks");
        company.setUser(companyUser);

        companyRepository.save(company);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        position.setCompany(company);
        traineeshipPositionRepository.save(position);

        // Create and save evaluation
        Evaluation evaluation = new Evaluation();
        evaluation.setMotivation(5);
        evaluation.setEfficiency(4);
        evaluation.setEffectiveness(5);
        evaluation.setfacilitiesAndGuidance(-1);
        evaluation.setEvaluationType(EvaluationType.COMPANY_EVAL);
        
        companyService.saveEvaluation(position.getId(), evaluation);
        
        // Retrieve and assert
        Optional<TraineeshipPosition> retrieved = traineeshipPositionRepository.findById(position.getId());
        Assertions.assertTrue(retrieved.isPresent());

        List<Evaluation> evaluations = retrieved.get().getEvaluations();
        Assertions.assertEquals(1, evaluations.size());

        Evaluation e = evaluations.get(0);
        Assertions.assertEquals(EvaluationType.COMPANY_EVAL, e.getEvaluationType());
        Assertions.assertEquals(5, e.getMotivation());
        Assertions.assertEquals(4, e.getEfficiency());
        Assertions.assertEquals(5, e.getEffectiveness());
        Assertions.assertEquals(-1, e.getFacilitiesAndGuidance());
    }

    @Test
    void testEvaluateAssignedPosition() {
        
        User companyUser = new User();
        companyUser.setUsername("comp4");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);
        
        // Create and save company and traineeship position
        Company company = new Company();
        company.setCompanyName("DevTech");
        company.setUser(companyUser);

        companyRepository.save(company);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        position.setCompany(company);
        traineeshipPositionRepository.save(position);

        // Check if evaluation already exists
        boolean evaluationExists = companyService.evaluateAssignedPosition(position.getId());
        assertFalse(evaluationExists); // No evaluations yet

        // Create and save a new evaluation
        Evaluation evaluation = new Evaluation();
        evaluation.setMotivation(5);
        evaluation.setEfficiency(4);
        evaluation.setEffectiveness(5);
        evaluation.setfacilitiesAndGuidance(-1);
        evaluation.setEvaluationType(EvaluationType.COMPANY_EVAL);
        
        companyService.saveEvaluation(position.getId(), evaluation);

        // Check again if the evaluation exists
        evaluationExists = companyService.evaluateAssignedPosition(position.getId());
        assertTrue(evaluationExists); // Now we have an evaluation
    }

    @Test
    void testDeletePosition() {
        
        User companyUser = new User();
        companyUser.setUsername("comp5");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);

        // Create and save company and traineeship position
        Company company = new Company();
        company.setCompanyName("TechVentures");
        company.setUser(companyUser);

        companyRepository.save(company);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        position.setCompany(company);
        traineeshipPositionRepository.save(position);

        // Delete position
        companyService.deletePosition(position.getId(), "comp5");

        // Ensure the position is deleted
        Optional<TraineeshipPosition> deletedPosition = traineeshipPositionRepository.findById(position.getId());
        assertFalse(deletedPosition.isPresent());
    }

    @Test
    void testDeletePositionSecurity() {
        
        User companyUser = new User();
        companyUser.setUsername("comp5");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);

        // Create and save company and traineeship position
        Company company = new Company();
        company.setCompanyName("InnoLab");
        company.setUser(companyUser);

        companyRepository.save(company);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setAssigned(true);
        position.setCompany(company);
        traineeshipPositionRepository.save(position);

        // Try deleting the position with a different company username
        assertThrows(SecurityException.class, () -> {
            companyService.deletePosition(position.getId(), "comp7");
        });
    }

    @Test
    void testPositionNotFoundOnDelete() {
        // Attempt to delete a non-existing position
        assertThrows(RuntimeException.class, () -> {
            companyService.deletePosition(999, "company5");
        });
    }
}

