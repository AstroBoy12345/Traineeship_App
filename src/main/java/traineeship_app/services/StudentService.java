package traineeship_app.services;

import java.util.List;

import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;

public interface StudentService {
	
	void saveProfile(Student student);
	Student retrieveProfile(String studentUsername);
	void saveLogBook(TraineeshipPosition position);
	

}
