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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Evaluation;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.domainmodel.User;
import traineeship_app.services.CompanyService;
import traineeship_app.services.UserService;

@Controller
public class CompanyController {
	
	@Autowired
    UserService userService;
	  
    @Autowired
    CompanyService companyService;
    
    
    @PostMapping("/company/details/save")
    public String saveCompanyDetails(@ModelAttribute("company") Company company, Principal principal) {
        // Παίρνεις τον User που είναι συνδεδεμένος
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        company.setUser(user); // Αυτόματα συνδέεται και παίρνει το username λόγω @MapsId
        companyService.saveProfile(company);
        user.setProfileCompleted(true);
        userService.saveUser(user);

        return "redirect:/company/dashboard";
    }
    
    @PostMapping("/company/companyprofile/save")
    public String saveProfile(@ModelAttribute("company") Company incoming, Model theModel,Principal principal) {
        
        // Πάρε τον πραγματικό χρήστη
        String actualUsername = principal.getName();

        // Φόρτωσε τον σωστό student από DB
        Company company = companyService.retrieveProfile(actualUsername);

        // Ενημέρωσε μόνο τα υπόλοιπα πεδία
        company.setCompanyName(incoming.getCompanyName());
        company.setCompanyLocation(incoming.getCompanyLocation());
     

        companyService.saveProfile(company);
        theModel.addAttribute("successMessage", "Το προφίλ ενημερώθηκε!");
        return "redirect:/company/dashboard";
    }
    
    @PostMapping("/company/dashboard/positionsNew/save")
    public String savePosition(@ModelAttribute("position") TraineeshipPosition position, Principal principal) {
        String username = principal.getName(); // Παίρνουμε το username της εταιρείας

        companyService.addPosition(username, position); // Μέθοδος στο service που αποθηκεύει τη θέση

        return "redirect:/company/dashboard"; // Επιστροφή στη λίστα διαθέσιμων θέσεων
    }
    
    @PostMapping("/company/dashboard/positions/delete")
    public String deletePosition(@RequestParam Integer id, Principal principal, RedirectAttributes redirectAttributes) {

            companyService.deletePosition(id, principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Η θέση διαγράφηκε.");

        return "redirect:/company/dashboard";
    }
    
    @PostMapping("/company/dashboard/positions/evaluate/{id}")
    public String submitEvaluation(@PathVariable Integer id, @ModelAttribute("evaluation") Evaluation evaluation, RedirectAttributes redirectAttributes) {

        companyService.saveEvaluation(id, evaluation);
        redirectAttributes.addFlashAttribute("successMessage", "Η αξιολόγηση καταχωρήθηκε.");
        return "redirect:/company/dashboard"; // ή όπου θέλεις να επιστρέψεις
    }
    
    
    
    //----------------------------------------------------------------------------------------------------------------------//
    
    
    
    @GetMapping("/company/companyprofile")
    public String retrieveProfile(Model model, Principal principal) {
        String username = principal.getName();
        Company company = companyService.retrieveProfile(username);
        model.addAttribute("company", company);
        
        return "company/companyprofile";
    }
    
    @GetMapping("/company/dashboard/posotions/availablepositions")
    public String listAvailablePositions(Model model,Principal principal) {
    	String username = principal.getName();
    	List<TraineeshipPosition> Avepositions = companyService.retrieveAvailablePositions(username);
    	model.addAttribute("positions", Avepositions);
    	
    	return "company/availablepositions";
    }
    
    @GetMapping("/company/dashboard/positions/assignedpositions")
    public String listAssignedPositions(Model model,Principal principal) {
    	String username = principal.getName();
    	List<TraineeshipPosition> AssignedPositions = companyService.retrieveAssignedPositions(username);
    	model.addAttribute("positions", AssignedPositions);
    	
    	return "company/assignedpositions";
    }
    
    @GetMapping("/company/addtraineeship")
    public String showPositionForm(Model model) {
        model.addAttribute("position", new TraineeshipPosition());
        
        return "company/addtraineeship";
    }
    
    @GetMapping("/company/dashboard/positions/del")
    public String deletePosition(Principal principal,Model model) {
    	
    		String username = principal.getName();
        	List<TraineeshipPosition> positions = companyService.retrieveAllPositions(username);
        	model.addAttribute("positions", positions);
        	
        return "company/Deletepositions";
    }
    
    @GetMapping("/company/dashboard/positions/evaluate/{id}")
    public String showEvaluationForm(@PathVariable Integer id, Model model) {
    	boolean alreadyEvaluated = companyService.evaluateAssignedPosition(id);
        model.addAttribute("evaluation", new Evaluation());
        model.addAttribute("positionId", id); 
        model.addAttribute("alreadyEvaluated", alreadyEvaluated);
        return "company/evaluationfrom";
    }
    
    @GetMapping("/company/companydetails")
    public String CompanyDetails(Model model) {
        model.addAttribute("company", new Company());
        return "company/companydetails"; 
    }
    
    @GetMapping("/company/dashboard")
    public String getStudentDashboard() {
        return "company/dashboard";
    }

}
