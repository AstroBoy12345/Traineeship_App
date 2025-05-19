package traineeship_app.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.mappers.TraineeshipPositionRepository;
import traineeship_app.services.StudentService;
import traineeship_app.services.UserService;

@Controller
public class StudentController {
	
	@Autowired
    UserService userService;
	
    @Autowired
    StudentService studentService;
    
	
	//before profile is completed
	 	@PostMapping("/student/details/save")
	    public String saveStudentDetails(@ModelAttribute("student") Student student, Principal principal) {
	        // Παίρνεις τον User που είναι συνδεδεμένος
	        User user = userService.findByUsername(principal.getName()).orElseThrow();

	        student.setUser(user); // Αυτόματα συνδέεται και παίρνει το username λόγω @MapsId
	        studentService.saveProfile(student);
	        user.setProfileCompleted(true);
	        userService.saveUser(user);

	        return "redirect:/student/dashboard";
	    }
	 
	 	@PostMapping("/student/dashboard/logbook/save")
	 	public String saveStudentLogBook(@ModelAttribute("logbook") TraineeshipPosition incoming, Model theModel, Principal principal) {
	 	    String username = principal.getName();
	 	    Student student = studentService.retrieveProfile(username);

	 	    // Φέρνεις το πραγματικό object από DB (με id)
	 	    TraineeshipPosition Position = student.getAssignedTraineeship();

	 	    // Κρατάς μόνο την αλλαγή στο logbook
	 	    Position.setStudentLogbook(incoming.getStudentLogbook());

	 	    // Τώρα το σώζεις με σωστό id
	 	    studentService.saveLogBook(Position);

	 	    theModel.addAttribute("successMessage", "Το logbook αποθηκεύτηκε με επιτυχία!");
	 	    return "redirect:/student/dashboard";
	 	}
	 
	    @PostMapping("/student/dashboard/profile/save")
	    public String saveProfile(@ModelAttribute("student") Student incoming, Model theModel,Principal principal) {
	        
	        // Πάρε τον πραγματικό χρήστη
	        String actualUsername = principal.getName();

	        // Φόρτωσε τον σωστό student από DB
	        Student student = studentService.retrieveProfile(actualUsername);

	        // Ενημέρωσε μόνο τα υπόλοιπα πεδία
	        student.setStudentName(incoming.getStudentName());
	        student.setAM(incoming.getAM());
	        student.setAvgGrade(incoming.getAvgGrade());
	        student.setInterests(incoming.getInterests());
	        student.setLookingForTraineeship(incoming.getLookingForTraineeship());
	        student.setSkills(incoming.getSkills());
	        student.setPreferredLocation(incoming.getPreferredLocation());
	     

	        studentService.saveProfile(student);
	        theModel.addAttribute("successMessage", "Το προφίλ ενημερώθηκε!");
	        return "redirect:/student/dashboard";
	    }
	 
	 
	 
	 //-------------------------------------------------------------------------------------------------------------------//
	 
	
	    @GetMapping("/student/studentprofile")
	    public String retrieveProfile(Model model, Principal principal) {
	        String username = principal.getName();
	        Student student = studentService.retrieveProfile(username);
	        model.addAttribute("student", student);
	        
	        return "student/studentprofile";
	    }
	 
	    @GetMapping("/student/logbook")
	    public String fillLogbook(Model model, Principal principal) {
	        String username = principal.getName();	 
	        Student student = studentService.retrieveProfile(username);
	        
	        TraineeshipPosition position = student.getAssignedTraineeship();
	        
	        model.addAttribute("position", position);
	        
	        return "student/logbook"; // 
	    }

	    @GetMapping("/student/studentdetails")
	    public String studentDetails(Principal principal, Model model) {
	        Student student = new Student();
	        model.addAttribute("student", student);
	        return "student/studentdetails";
	    }


	    @GetMapping("/student/dashboard")
	    public String getStudentDashboard() {
	        return "student/dashboard";
	    }

}
