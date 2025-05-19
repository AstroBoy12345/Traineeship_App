package traineeship_app.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import traineeship_app.domainmodel.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
	Optional<Evaluation> findById(Integer id);
}

