package traineeship_app.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.User;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfessorRepositoryTest {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Test
    void testFindByUsername() {
        User proffessorUser = new User();
        proffessorUser.setUsername("prof1");
        proffessorUser.setPassword("1234");
        proffessorUser.setRole("Professor");
        proffessorUser.setProfileCompleted(true);

        Professor professor = new Professor();
        professor.setProfessorName("Prof. John Doe");
        professor.setInterests("AI, ML, Data Science");
        professor.setUser(proffessorUser); 

        professorRepository.save(professor);

        Professor found = professorRepository.findByUsername("prof1");
        Assertions.assertNotNull(found, "Professor should not be null");
        Assertions.assertEquals("prof1", found.getUsername(), "Username should match");
        Assertions.assertEquals("Prof. John Doe", found.getProfessorName(), "Professor name should match");
        Assertions.assertEquals("AI, ML, Data Science", found.getInterests(), "Interests should match");
       
    }
	
    @Test
    void testFindByUsername_ShouldReturnNull_WhenNotFound() {
        Professor found = professorRepository.findByUsername("unknown_professor");
        assertThat(found).isNull();
    }
	
}
