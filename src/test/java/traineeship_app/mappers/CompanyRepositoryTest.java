package traineeship_app.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.User;



@DataJpaTest
@ActiveProfiles("test") 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void testSaveAndFindCompany() {
        User companyUser = new User();
        companyUser.setUsername("comp1");
        companyUser.setPassword("1234");
        companyUser.setRole("Company");
        companyUser.setProfileCompleted(true);

        Company company = new Company();
        company.setCompanyName("TechCorp");
        company.setCompanyLocation("123 Main Street");
        company.setUser(companyUser);

        companyRepository.save(company);

    
        Company foundCompany = companyRepository.findByUsername("comp1");

   
        Assertions.assertNotNull(foundCompany);
        Assertions.assertEquals("comp1", foundCompany.getUsername());
        Assertions.assertEquals("TechCorp", foundCompany.getCompanyName());
        Assertions.assertEquals("123 Main Street", foundCompany.getCompanyLocation());
    }

    @Test
    void testFindByUsername_ShouldReturnNull_WhenNotFound() {
        Company foundCompany = companyRepository.findByUsername("unknown_company");
        assertThat(foundCompany).isNull(); 
    }
}
