package traineeship_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.StudentRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TraineeshipPositionRepository traineeshippositionRepository;

	@Override
	public void saveProfile(Student student) {
        studentRepository.save(student);
	}

	@Override
	public Student retrieveProfile(String studentUsername) {
		return studentRepository.findByUsername(studentUsername)
	            .orElseThrow(() -> new UsernameNotFoundException("Student not found: " + studentUsername));
	}

	@Override
	public void saveLogBook(TraineeshipPosition position) {
		
		Optional<TraineeshipPosition> pos = traineeshippositionRepository.findById(position.getId());
		
		 if (pos.isPresent()) {
			 TraineeshipPosition existing = pos.get();
	            existing.setStudentLogbook(position.getStudentLogbook()); // αποθήκευση νέου logbook
	            traineeshippositionRepository.save(existing);
	        } else {
	            throw new EntityNotFoundException("TraineeshipPosition not found with ID: " + position.getId());
	        }
		
	}

}
