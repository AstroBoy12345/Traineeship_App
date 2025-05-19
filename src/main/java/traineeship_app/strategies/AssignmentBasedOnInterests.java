package traineeship_app.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.ProfessorRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

@Component
public class AssignmentBasedOnInterests implements SupervisorAssignmentStrategy{
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	private static final double THRESHOLD = 0.4;

	@Override
	public void assign(Integer positionId) {
	    Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
	    if (positionOpt.isEmpty()) {
	        System.out.println("Position not found.");
	        return;
	    }

	    TraineeshipPosition position = positionOpt.get();
	    Set<String> positionTopics = parse(position.getTopics());

	    List<Professor> professors = professorRepository.findAll();
	    Professor bestProfessor = null;
	    double bestSimilarity = 0.0;

	    for (Professor professor : professors) {
	        Set<String> professorInterests = parse(professor.getInterests());
	        double similarity = jaccardSimilarity(professorInterests, positionTopics);

	        System.out.println("JACCARD SIMILARITY FOR PROFESSOR:");
	        System.out.println(professor.getProfessorName() + " => " + similarity);

	        if (similarity >= THRESHOLD && similarity > bestSimilarity) {
	            bestSimilarity = similarity;
	            bestProfessor = professor;
	        }
	    }

	    if (bestProfessor != null) {
	        position.setProfessor(bestProfessor); // χρειάζεται setter στη θέση
	        traineeshipPositionRepository.save(position);  // αποθήκευση ανάθεσης
	        System.out.println("Professor assigned: " + bestProfessor.getProfessorName());
	    } else {
	        System.out.println("No professor matched the required threshold.");
	    }
	}
	
	private Set<String> parse(String text) {
        if (text == null || text.isBlank()) return Set.of();
        return Arrays.stream(text.split(","))
                     .map(String::trim)
                     .map(String::toLowerCase)
                     .collect(Collectors.toSet());
	}
	
	private double jaccardSimilarity(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty() || set2.isEmpty()) return 0.0;

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

}
