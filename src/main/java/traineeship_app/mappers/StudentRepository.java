package traineeship_app.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import traineeship_app.domainmodel.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUsername(String username);
	Optional<Student> findByPreferredLocation(String location);
	List<Student> findByLookingForTraineeshipTrue();
} 
    

