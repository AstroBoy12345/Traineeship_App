package traineeship_app.strategies;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.ProfessorRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

@Component
public class AssignmentBasedOnLoad implements SupervisorAssignmentStrategy {

	@Autowired
    private  ProfessorRepository professorRepository;
    
	@Autowired
    private  TraineeshipPositionRepository positionRepository;



    @Override
    public void assign(Integer positionId) {
        // Βρες τη θέση
    	Optional<TraineeshipPosition> position = positionRepository.findById(positionId);

        // Βρες όλους τους καθηγητές
        List<Professor> professors = professorRepository.findAll();

        // Βρες αυτόν με τις λιγότερες αναθέσεις
        Professor leastLoaded = professors.stream()
                .min(Comparator.comparingInt(p -> p.getSupervisedPositions().size()))
                .orElseThrow(() -> new IllegalStateException("No professors available"));

        // Ανάθεσε τον καθηγητή
        position.get().setProfessor(leastLoaded);

        // Ενημέρωσε και τη λίστα του καθηγητή (προαιρετικά)
        leastLoaded.getSupervisedPositions().add(position.get());

        // Κάνε save τη θέση
        positionRepository.save(position.get());
    }
}