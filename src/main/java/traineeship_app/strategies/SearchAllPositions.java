package traineeship_app.strategies;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import traineeship_app.domainmodel.TraineeshipPosition;

import traineeship_app.mappers.TraineeshipPositionRepository;

@Component
public class SearchAllPositions implements PositionSearchStrategy{
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;

	@Override
	public List<TraineeshipPosition> search(String applicantUsername) {

		List<TraineeshipPosition> traineeshipPositions = traineeshipPositionRepository.findByStudentIsNull();
		
		return traineeshipPositions;
	}

}
