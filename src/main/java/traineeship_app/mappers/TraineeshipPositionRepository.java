package traineeship_app.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import traineeship_app.domainmodel.TraineeshipPosition;

@Repository
public interface TraineeshipPositionRepository extends JpaRepository<TraineeshipPosition, Integer> {
    TraineeshipPosition findByTitle(String title);
    List<TraineeshipPosition> findByCompany_UsernameAndIsAssignedFalse(String username);
    List<TraineeshipPosition> findByCompany_UsernameAndIsAssignedTrue(String username);
    List<TraineeshipPosition> findByProfessor_UsernameAndIsAssignedTrue(String username);
    List<TraineeshipPosition> findByIsAssignedTrueAndProfessorIsNull();
    List<TraineeshipPosition> findByIsAssignedTrue();
    List<TraineeshipPosition> findByCompany_Username(String username);
    List<TraineeshipPosition> findByStudentIsNull();
   
    
}
