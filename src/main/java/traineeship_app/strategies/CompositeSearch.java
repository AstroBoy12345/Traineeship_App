package traineeship_app.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import traineeship_app.domainmodel.TraineeshipPosition;

@Component
public class CompositeSearch implements PositionSearchStrategy{
	
    @Autowired
    private SearchBasedOnInterests searchBasedOnInterests;

    @Autowired
    private SearchBasedOnLocation searchBasedOnLocation;

    @Override
    public List<TraineeshipPosition> search(String applicantUsername) {
        List<TraineeshipPosition> byInterests = searchBasedOnInterests.search(applicantUsername);
        List<TraineeshipPosition> byLocation = searchBasedOnLocation.search(applicantUsername);

        // Κάνε intersection με βάση το ID ή object reference
        Set<TraineeshipPosition> locationSet = new HashSet<>(byLocation);
        List<TraineeshipPosition> result = byInterests.stream()
            .filter(locationSet::contains)
            .collect(Collectors.toList());

        return result;
    }

}
