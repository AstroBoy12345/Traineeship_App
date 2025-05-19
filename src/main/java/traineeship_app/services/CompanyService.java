package traineeship_app.services;

import java.util.List;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.TraineeshipPosition;

public interface CompanyService {
	
	Company retrieveProfile(String username);

    void saveProfile(Company company);

    List<TraineeshipPosition> retrieveAvailablePositions(String username);

    void addPosition(String username, TraineeshipPosition position);

    List<TraineeshipPosition> retrieveAssignedPositions(String username);

    boolean evaluateAssignedPosition(Integer positionId);

    void saveEvaluation(Integer positionId, Evaluation evaluation);
    
    public List<TraineeshipPosition> retrieveAllPositions(String username);
    
    void deletePosition(Integer id, String companyUsername);

}
