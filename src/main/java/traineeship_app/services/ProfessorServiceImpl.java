package traineeship_app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.EvaluationType;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.ProfessorRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

@Service
public class ProfessorServiceImpl implements ProfessorService{
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;

	@Override
	public Professor retrieveProfile(String username) {
		return professorRepository.findByUsername(username);

	}

	@Override
	public void saveProfile(Professor professor) {
		professorRepository.save(professor);
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions(String username) {
		return traineeshipPositionRepository.findByProfessor_UsernameAndIsAssignedTrue(username);
	}

	public boolean evaluateAssignedPosition(Integer positionId) {
	    TraineeshipPosition position = traineeshipPositionRepository.findById(positionId)
	        .orElseThrow(() -> new RuntimeException("Η θέση δεν βρέθηκε."));

	    if (!position.isAssigned()) {
	        throw new RuntimeException("Η θέση δεν έχει ανατεθεί σε φοιτητή.");
	    }

	    // Έλεγχος για υπάρχουσα αξιολόγηση τύπου COMPANY_EVAL
	    for (Evaluation e : position.getEvaluations()) {
	        if (e.getEvaluationType() == EvaluationType.PROFESSOR_EVAL) {
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
	    
	    evaluation.setId(null);
	    evaluation.setEvaluationType(EvaluationType.PROFESSOR_EVAL);
	    position.getEvaluations().add(evaluation);
	    traineeshipPositionRepository.save(position);	
	}

}
