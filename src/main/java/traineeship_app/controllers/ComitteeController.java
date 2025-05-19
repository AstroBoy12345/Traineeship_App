package traineeship_app.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.TraineeshipPosition;
import traineeship_app.services.CommitteeService;
import traineeship_app.services.ProfessorService;
import traineeship_app.services.StudentService;

@Controller
public class ComitteeController {

	@Autowired
	CommitteeService committeeService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	ProfessorService professorService;
	
	@GetMapping("/committee/dashboard/positions")
	public String findPositions(@RequestParam("studentUsername") String studentUsername, @RequestParam(name = "strategy", required = false) String strategy, Model model) {
		
	    // Αν δεν δόθηκε στρατηγική, κάνε default σε "all"
	    if (strategy == null || strategy.isBlank()) {
	        strategy = "all";
	    }
		
		List<TraineeshipPosition> positions = committeeService.retrievePositionsForApplicant(studentUsername, strategy);
		model.addAttribute("positions", positions);
		model.addAttribute("studentUsername", studentUsername);
		model.addAttribute("strategy", strategy); // για να γεμίζει σωστά το dropdown
		return "committee/positions";
		}
	
	
    @GetMapping("/committee/dashboard")
    public String getStudentDashboard() {
        return "committee/committeedashboard";
    }
    
    
    @GetMapping("/committee/students")
    public String listAllStudents(Model model) {
        List<Student> students = committeeService.retrieveTraineeshipApplications();
        model.addAttribute("students", students);
        return "committee/students"; // θα χρειαστεί students.html
    }
    
    
    
    @PostMapping("/committee/assign")
    public String assignPosition(@RequestParam("positionId") Integer positionId, @RequestParam("studentUsername") String studentUsername,RedirectAttributes redirectAttributes) {
        try {
        	committeeService.assignPosition(positionId, studentUsername);
            redirectAttributes.addFlashAttribute("successMessage", "Position and supervisor successfully assigned.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error during assignment: " + e.getMessage());
        }

        return "redirect:/committee/positions/" + positionId + "/summary";
    }
    
    @GetMapping("/committee/positions/{id}/summary")
    public String showAssignmentSummary(@PathVariable("id") Integer positionId, Model model) {
        Optional<TraineeshipPosition> positionOpt = committeeService.Traineeship(positionId);

        if (positionOpt.isPresent()) {
            TraineeshipPosition position = positionOpt.get();
            model.addAttribute("position", position);
            return "committee/assignmentSummary";
        } else {
            return "redirect:/committee/dashboard"; // ή error page
        }
    }
    
    @GetMapping("/committee/assigned-traineeships")
    public String viewAssignedTraineeships(Model model) {
        List<TraineeshipPosition> assignedPositions = committeeService.listAssignedTraineeships();

        model.addAttribute("positions", assignedPositions);
        return "committee/assignProfessor";
    }
    
    @PostMapping("/committee/assign-supervisor")
    public String assignProfessor(@RequestParam Integer positionId,
                                  @RequestParam String strategy,
                                  RedirectAttributes redirectAttributes) {
        try {
            committeeService.assignSupervisor(positionId, strategy);
            return "redirect:/committee/positions/" + positionId + "/summary";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to assign professor: " + e.getMessage());
            return "redirect:/committee/assigned-traineeships";
        }
    }
    @GetMapping("/committee/traineeships")
    public String listAllTraineeships(Model model) {
        List<TraineeshipPosition> positions = committeeService.listAssignedTraineeships();     
        model.addAttribute("availablePositions", positions);
        return "committee/traineeships"; // Χρειάζεται traineeships.html
    }
    
    @GetMapping("/committee/final-reports")
    public String showFinalizablePositions(Model model) {
        List<TraineeshipPosition> positions = committeeService.listAssignedTraineeshipsFinal();
        List<TraineeshipPosition> availablePositions = positions.stream()
        	    .filter(p -> p.getStudent() != null)
        	    .collect(Collectors.toList());// Προσαρμόζεται αν έχεις πεδίο evaluation
        model.addAttribute("positions", availablePositions);
        return "committee/finalReports";
    }
    
    @PostMapping("/committee/finalize")
    public String finalizeTraineeship(@RequestParam("positionId") Integer positionId,
                                      @RequestParam("passFail") boolean pass
                                      ) {  
        committeeService.completeAssignedTraineeships(positionId,pass);


        return "redirect:/committee/final-reports";
    }

}
