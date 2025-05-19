package traineeship_app.domainmodel;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
class EvaluationTest {

    private EvaluationType evaluationType;

    @Test
    void testUserCreation() {
        Evaluation evaluation = new Evaluation(); //every role works no need to test for different roles
        

        evaluation.setEvaluationType(evaluationType);
        evaluation.setMotivation(7);
        evaluation.setEffectiveness(9); 
        evaluation.setEfficiency(5); 
        
        assertEquals(evaluationType, evaluation.getEvaluationType());
        assertEquals(7, evaluation.getMotivation());
        assertEquals(5, evaluation.getEfficiency());
        assertEquals(9, evaluation.getEffectiveness());
        
    }
}

