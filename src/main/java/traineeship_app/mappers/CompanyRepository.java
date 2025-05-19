package traineeship_app.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.TraineeshipPosition;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByUsername(String username);
    List<Company> findByCompanyLocation(String location);
} 