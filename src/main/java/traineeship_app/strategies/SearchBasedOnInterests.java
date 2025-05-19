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

import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.StudentRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;



@Component
public class SearchBasedOnInterests implements PositionSearchStrategy{
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private static final double THRESHOLD = 0.4;

	@Override
	public List<TraineeshipPosition> search(String applicantUsername) {
		
		Optional<Student> student = studentRepository.findByUsername(applicantUsername);
		String Interests = student.get().getInterests();
		Set<String> studentInterests = parse(Interests);
		
		List<TraineeshipPosition> allPositions = traineeshipPositionRepository.findByStudentIsNull();
	    List<TraineeshipPosition> matchingPositions = new ArrayList<>();
	    
	    for (TraineeshipPosition position : allPositions) {

            Set<String> positionTopics = parse(position.getTopics());

            double similarity = jaccardSimilarity(studentInterests, positionTopics);

            System.out.println("JACCARD IMILARITY FOR:");
            System.out.println(position.getTitle());
            System.out.println("==");
            System.out.println(similarity);
            if (similarity >= THRESHOLD) {
                matchingPositions.add(position);
            }
	    }

		return matchingPositions;
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
