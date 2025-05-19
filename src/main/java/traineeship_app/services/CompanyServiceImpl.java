package traineeship_app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.EvaluationType;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.CompanyRepository;
import traineeship_app.mappers.EvaluationRepository;
import traineeship_app.mappers.ProfessorRepository;
import traineeship_app.mappers.StudentRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
    @Autowired
    private TraineeshipPositionRepository traineeshipPositionRepository;
    
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
    
    
	@Override
	public Company retrieveProfile(String username) {
		return companyRepository.findByUsername(username);
	}

	@Override
	public void saveProfile(Company company) {
		companyRepository.save(company);
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAvailablePositions(String username) {
		return traineeshipPositionRepository.findByCompany_UsernameAndIsAssignedFalse(username);
	}

	@Override
	public void addPosition(String username, TraineeshipPosition position) {
		 Company company = companyRepository.findByUsername(username);
	        if (company != null) {
	            position.setCompany(company);
	            position.setAssigned(false);
	            traineeshipPositionRepository.save(position);
	        }
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions(String username) {
		return traineeshipPositionRepository.findByCompany_UsernameAndIsAssignedTrue(username);
	}
	
	@Override
	public List<TraineeshipPosition> retrieveAllPositions(String username) {
		return traineeshipPositionRepository.findByCompany_Username(username);
	}

	public boolean evaluateAssignedPosition(Integer positionId) {
	    TraineeshipPosition position = traineeshipPositionRepository.findById(positionId)
	        .orElseThrow(() -> new RuntimeException("Η θέση δεν βρέθηκε."));

	    if (!position.isAssigned()) {
	        throw new RuntimeException("Η θέση δεν έχει ανατεθεί σε φοιτητή.");
	    }

	    // Έλεγχος για υπάρχουσα αξιολόγηση τύπου COMPANY_EVAL
	    for (Evaluation e : position.getEvaluations()) {
	        if (e.getEvaluationType() == EvaluationType.COMPANY_EVAL) {
	            return true; // Υπάρχει ήδη
	        }
	    }
	    return false;
	}

	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {
		TraineeshipPosition position = traineeshipPositionRepository.findById(positionId)
	            .orElseThrow(() -> new RuntimeException("Position not found"));
		
		 // Μπορείς να κάνεις null check αν δεν έχει αρχικοποιηθεί
	    if (position.getEvaluations() == null) {
	        position.setEvaluations(new ArrayList<>());
	    }


	    evaluation.setfacilitiesAndGuidance(-1);
	    
	    evaluation.setId(null);
	    evaluation.setEvaluationType(EvaluationType.COMPANY_EVAL);
	    position.getEvaluations().add(evaluation);
	    traineeshipPositionRepository.save(position);	
	}

	@Override
	public void deletePosition(Integer id, String companyUsername) {
	    Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(id);
	    if (positionOpt.isPresent()) {
	        TraineeshipPosition position = positionOpt.get();
	        if (!position.getCompany().getUsername().equals(companyUsername)) {
	            throw new SecurityException("Η θέση δεν ανήκει σε αυτή την εταιρεία.");
	        }

	        // Σπάσε τις συνδέσεις πριν το delete
	        if (position.getStudent() != null) {
	            Student student = position.getStudent();
	            Professor professor = position.getProfessor();
	            student.setAssignedTraineeship(null);
	            professor.getSupervisedPositions().remove(position);
	            studentRepository.save(student);
	            professorRepository.save(professor);
	            position.setStudent(null);
	        }

	        if (position.getEvaluations() != null) {
	            position.getEvaluations().clear();
	        }

	        position.setProfessor(null);
	        position.setCompany(null);

	        traineeshipPositionRepository.delete(position);
	    } else {
	        throw new EntityNotFoundException("Δεν βρέθηκε η θέση με ID: " + id);
	    }
	}

}
