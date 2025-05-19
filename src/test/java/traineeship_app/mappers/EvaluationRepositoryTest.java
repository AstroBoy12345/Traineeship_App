package traineeship_app.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.EvaluationType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest 
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EvaluationRepositoryTest {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Test
    void testSaveAndFindEvaluation() {

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.COMPANY_EVAL);
        evaluation.setMotivation(8);
        evaluation.setEfficiency(9);
        evaluation.setEffectiveness(7);


        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        Integer evaluationId = savedEvaluation.getId();


        Optional<Evaluation> foundEvaluation = evaluationRepository.findById(evaluationId);

        Assertions.assertTrue(foundEvaluation.isPresent());
        Assertions.assertEquals(EvaluationType.COMPANY_EVAL, foundEvaluation.get().getEvaluationType());
        Assertions.assertEquals(8, foundEvaluation.get().getMotivation());
        Assertions.assertEquals(9, foundEvaluation.get().getEfficiency());
        Assertions.assertEquals(7, foundEvaluation.get().getEffectiveness());
    }

    @Test
    void testDeleteEvaluation() {
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.COMPANY_EVAL);
        evaluation.setMotivation(5);
        evaluation.setEfficiency(6);
        evaluation.setEffectiveness(4);

        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        Integer evaluationId = savedEvaluation.getId();

        evaluationRepository.deleteById(evaluationId);


        Optional<Evaluation> deletedEvaluation = evaluationRepository.findById(evaluationId);
        assertThat(deletedEvaluation).isEmpty();
    }
}
