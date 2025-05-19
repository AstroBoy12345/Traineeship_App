package traineeship_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import traineeship_app.domainmodel.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByUsername(String username);
} 
