package traineeship_app.services;

import java.util.List;
import java.util.Optional;

import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;

public interface CommitteeService {
	
    List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String strategy);

    List<Student> retrieveTraineeshipApplications();

    void assignPosition(Integer positionId, String studentUsername);

    void assignSupervisor(Integer positionId, String strategy);

    List<TraineeshipPosition> listAssignedTraineeships();

    void completeAssignedTraineeships(Integer positionId, boolean pass);

	Optional<TraineeshipPosition> Traineeship(Integer positionId);

	List<TraineeshipPosition> listAssignedTraineeshipsFinal();



}
