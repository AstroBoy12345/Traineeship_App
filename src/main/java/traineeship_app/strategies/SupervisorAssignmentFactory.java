package traineeship_app.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupervisorAssignmentFactory {

	@Autowired
    private AssignmentBasedOnLoad assignmentBasedOnLoad;
    
    @Autowired
    private AssignmentBasedOnInterests assignmentBasedOnInterests;


    public SupervisorAssignmentStrategy create(String strategy) {
        if ("load".equalsIgnoreCase(strategy)) {
            return assignmentBasedOnLoad;
        } else if ("interests".equalsIgnoreCase(strategy)) {
            return assignmentBasedOnInterests;
        } else {
            throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }
    }
}