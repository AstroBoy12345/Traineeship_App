package traineeship_app.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionSearchFactory {
	
    @Autowired
    private CompositeSearch compositeSearch;
    
    @Autowired
    private SearchAllPositions all;

    @Autowired
    private SearchBasedOnInterests searchBasedOnInterests;

    @Autowired
    private SearchBasedOnLocation searchBasedOnLocation;
	
	
	
    public PositionSearchStrategy create(String strategy) {
        switch (strategy.toLowerCase()) {
            case "location":
                return searchBasedOnLocation;
            case "interests":
                return searchBasedOnInterests;
            case "composite":
                return compositeSearch;
            case "all":
                return all;
            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }
    }
}
