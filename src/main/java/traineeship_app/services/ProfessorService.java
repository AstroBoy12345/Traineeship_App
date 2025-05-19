package traineeship_app.services;

import java.util.List;

import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.TraineeshipPosition;

public interface ProfessorService {
	
    Professor retrieveProfile(String username);

    void saveProfile(Professor professor);

    List<TraineeshipPosition> retrieveAssignedPositions(String username);

    boolean evaluateAssignedPosition(Integer positionId);

    void saveEvaluation(Integer positionId, Evaluation evaluation);


}
