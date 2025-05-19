package traineeship_app.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.CompanyRepository;
import traineeship_app.mappers.StudentRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

@Component
public class SearchBasedOnLocation implements PositionSearchStrategy{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;

	@Override
	public List<TraineeshipPosition> search(String applicantUsername) {

	    Optional<Student> student = studentRepository.findByUsername(applicantUsername);

	    String location = student.get().getPreferredLocation();
	    List<Company> companies = companyRepository.findByCompanyLocation(location);

	    List<TraineeshipPosition> positions = new ArrayList<>();
	    for (Company company : companies) {
	        positions.addAll(company.getPositions());
	    }

	    // Φιλτράρισμα: κράτησε μόνο όσες έχουν student == null
	    List<TraineeshipPosition> availablePositions = new ArrayList<>();
	    for (TraineeshipPosition position : positions) {
	        if (position.getStudent() == null) {
	            availablePositions.add(position);
	        }
	    }

	    return availablePositions;
	}
}
