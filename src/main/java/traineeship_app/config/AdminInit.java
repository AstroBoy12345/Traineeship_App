package traineeship_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.mappers.TraineeshipPositionRepository;
import traineeship_app.services.CompanyService;
import traineeship_app.services.ProfessorService;
import traineeship_app.services.StudentService;
import traineeship_app.services.UserService;

@Configuration
public class AdminInit {
	

	@Autowired
	StudentService studentService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	TraineeshipPositionRepository traineeshipPositionRepository;
	

	
	@Bean
	public CommandLineRunner initAdmins(UserService userService, PasswordEncoder passwordEncoder) {
	    return args -> {

	    	// ➤ Create Company User
	        if (!userService.isUserPresent("testcompany")) {
	            User companyUser = new User();
	            companyUser.setUsername("testcompany");
	            companyUser.setPassword(passwordEncoder.encode("1234"));
	            companyUser.setRole("Company");
	            companyUser.setProfileCompleted(true);

	            Company company = new Company();
	            company.setCompanyName("TechCorp");
	            company.setCompanyLocation("Athens");
	            company.setUser(companyUser);
	            companyService.saveProfile(company);
	        }

	        // ➤ Create Professor User
	        if (!userService.isUserPresent("testprofessor")) {
	            User professorUser = new User();
	            professorUser.setUsername("testprofessor");
	            professorUser.setPassword(passwordEncoder.encode("1234"));
	            professorUser.setRole("Professor");
	            professorUser.setProfileCompleted(true);

	            Professor professor = new Professor();
	            professor.setProfessorName("Dr. Nikos Karalis");
	            professor.setInterests("Spring Boot, Git, REST");
	            professor.setUser(professorUser);
	            professorService.saveProfile(professor);
	        }

	        // ➤ Create Student User
	        if (!userService.isUserPresent("teststudent")) {
	            User studentUser = new User();
	            studentUser.setUsername("teststudent");
	            studentUser.setPassword(passwordEncoder.encode("1234"));
	            studentUser.setRole("Student");
	            studentUser.setProfileCompleted(true);


	            Student student = new Student();
	            student.setStudentName("Νεκταρία Παπαδοπούλου");
	            student.setAM("2023001");
	            student.setPreferredLocation("ioannina");
	            student.setInterests("git,spring boot");
	            student.setLookingForTraineeship(true);
	            student.setUser(studentUser);
	            studentService.saveProfile(student);

	            // ➤ Create Traineeship Position
	            Company company = companyService.retrieveProfile("testcompany");
	            Professor professor = professorService.retrieveProfile("testprofessor");

	            TraineeshipPosition position = new TraineeshipPosition();
	            position.setTitle("Πρακτική Ανάπτυξης Λογισμικού");
	            position.setDescription("Συμμετοχή σε έργα Java/Spring.");
	            position.setTopics("Spring Boot, Git, REST");
	            position.setSkills("Java, Teamwork");
	            position.setCompany(company);
	            position.setStudentLogbook("Αρχική καταγραφή logbook...");

	            traineeshipPositionRepository.save(position);

	            student.setAssignedTraineeship(position);
	            studentService.saveProfile(student);
	        }
	        
	        // ➤ Committee Members
	        if (!userService.isUserPresent("committee1")) {
	            User user1 = new User();
	            user1.setUsername("committee1");
	            user1.setPassword(passwordEncoder.encode("1234"));
	            user1.setRole("Committee");
	            user1.setProfileCompleted(true);
	            userService.saveUser(user1);
	        }

	        if (!userService.isUserPresent("committee2")) {
	            User user2 = new User();
	            user2.setUsername("committee2");
	            user2.setPassword(passwordEncoder.encode("1234"));
	            user2.setRole("Committee");
	            user2.setProfileCompleted(true);
	            userService.saveUser(user2);
	        }
	    };
	}
}