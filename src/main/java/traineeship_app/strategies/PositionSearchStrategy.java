package traineeship_app.strategies;

import java.util.List;

import traineeship_app.domainmodel.TraineeshipPosition;

public interface PositionSearchStrategy {
	
	List<TraineeshipPosition> search(String applicantUsername);

}
