package traineeship_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.mappers.StudentRepository;
import traineeship_app.mappers.TraineeshipPositionRepository;
import traineeship_app.strategies.PositionSearchFactory;
import traineeship_app.strategies.PositionSearchStrategy;
import traineeship_app.strategies.SupervisorAssignmentFactory;
import traineeship_app.strategies.SupervisorAssignmentStrategy;

@Service
public class CommitteeServiceImpl implements CommitteeService{
	
	@Autowired
	PositionSearchFactory positionsSearchFactory;
	
	@Autowired
	SupervisorAssignmentFactory supervisorAssignmentFactory;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TraineeshipPositionRepository traineeshipPositionRepository;


	@Override
	public List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String strategy) {
		PositionSearchStrategy positionSearchStrategy;	
		positionSearchStrategy = positionsSearchFactory.create(strategy);	
		List<TraineeshipPosition> positions = positionSearchStrategy.search(applicantUsername);
		
		return positions;
	}

	@Override
	public List<Student> retrieveTraineeshipApplications() {
		return studentRepository.findByLookingForTraineeshipTrue();
	}

	@Override
	public void assignPosition(Integer positionId, String studentUsername) {
		
		  Optional<TraineeshipPosition> positionOpt = traineeshipPositionRepository.findById(positionId);
		  Optional<Student> studentOpt = studentRepository.findByUsername(studentUsername);
		  TraineeshipPosition position = positionOpt.get();
	      Student student = studentOpt.get();
	      position.setAssigned(true);
	      position.setStudent(student);
	      student.setAssignedTraineeship(position);
	      student.setLookingForTraineeship(false);
	      traineeshipPositionRepository.save(position);
		
	}

	@Override
	public void assignSupervisor(Integer positionId, String strategy) {
	    	supervisorAssignmentFactory.create(strategy).assign(positionId);
	}

	@Override
	public Optional<TraineeshipPosition> Traineeship(Integer positionId) {
		return traineeshipPositionRepository.findById(positionId);
	}

	@Override
	public void completeAssignedTraineeships(Integer positionId, boolean pass) {
		 Optional<TraineeshipPosition> opt = traineeshipPositionRepository.findById(positionId);
		 TraineeshipPosition pos = opt.get();
         pos.setPassFailGrade(pass);
         traineeshipPositionRepository.save(pos);
	}

	@Override
	public List<TraineeshipPosition> listAssignedTraineeships() {
		return traineeshipPositionRepository.findByIsAssignedTrueAndProfessorIsNull();
	}
	
	@Override
	public List<TraineeshipPosition> listAssignedTraineeshipsFinal() {
		return traineeshipPositionRepository.findByIsAssignedTrue();
	}
	
	

}
