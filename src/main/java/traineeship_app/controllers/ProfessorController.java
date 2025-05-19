package traineeship_app.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.services.ProfessorService;
import traineeship_app.services.UserService;

@Controller
public class ProfessorController {
	
	@Autowired
    UserService userService;
	
	@Autowired
    ProfessorService professorService;

	
    @PostMapping("/professor/details/save")
    public String saveProfessorDetails(@ModelAttribute("professor") Professor professor, Principal principal) {
        // Παίρνεις τον User που είναι συνδεδεμένος
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        professor.setUser(user); // Αυτόματα συνδέεται και παίρνει το username λόγω @MapsId
        professorService.saveProfile(professor);
        user.setProfileCompleted(true);
        userService.saveUser(user);

        return "redirect:/professor/dashboard";
    }
    
    @PostMapping("/professor/profile/save")
    public String saveProfile(@ModelAttribute("professor") Professor incoming, Model theModel,Principal principal) {
        
        // Πάρε τον πραγματικό χρήστη
        String actualUsername = principal.getName();

        // Φόρτωσε τον σωστό student από DB
        Professor professor = professorService.retrieveProfile(actualUsername);

        professor.setInterests(incoming.getInterests());
        professor.setProfessorName(incoming.getProfessorName());

        professorService.saveProfile(professor);
        theModel.addAttribute("successMessage", "Το προφίλ ενημερώθηκε!");
        return "redirect:/professor/dashboard";
    }
    
    @PostMapping("/professor/dashboard/positions/evaluate/{id}")
    public String submitEvaluation(@PathVariable Integer id, @ModelAttribute("evaluation") Evaluation evaluation, RedirectAttributes redirectAttributes) {

        professorService.saveEvaluation(id, evaluation);
        redirectAttributes.addFlashAttribute("successMessage", "Η αξιολόγηση καταχωρήθηκε.");
        return "redirect:/professor/dashboard"; // ή όπου θέλεις να επιστρέψεις
    }
    
    
    
    //------------------------------------------------------------------------------------------------------------------------//
    
    @GetMapping("/professor/professorprofile")
    public String retrieveProfile(Model model, Principal principal) {
        String username = principal.getName();
        Professor professor = professorService.retrieveProfile(username);
        model.addAttribute("professor", professor);
        
        return "professor/professorprofile";
    }
    
    @GetMapping("/professor/dashboard/positions/supervisedpositions")
    public String assignedPositions(Model model, Principal principal) {
    	String username = principal.getName();
    	List<TraineeshipPosition> AssignedPositions = professorService.retrieveAssignedPositions(username);
    	model.addAttribute("positions", AssignedPositions);
    	
    	return "professor/supervisedpositions";
    }
    
    @GetMapping("/professor/dashboard/positions/evaluate/{id}")
    public String showEvaluationForm(@PathVariable Integer id, Model model) {
    	boolean alreadyEvaluated = professorService.evaluateAssignedPosition(id);
        model.addAttribute("evaluation", new Evaluation());
        model.addAttribute("positionId", id); 
        model.addAttribute("alreadyEvaluated", alreadyEvaluated);
        return "professor/evaluationfrom";
    }
    
    @GetMapping("/professor/professordetails")
    public String CompanyDetails(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/professordetails"; 
    }
    
    @GetMapping("/professor/dashboard")
    public String getStudentDashboard() {
        return "professor/professordashboard";
    }
    
}
