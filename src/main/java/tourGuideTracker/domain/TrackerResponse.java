package tourGuideTracker.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import tourGuideTracker.domain.location.Attraction;
import tourGuideTracker.domain.location.VisitedLocation;

public class TrackerResponse {
    public final VisitedLocation visitedLocation;
    public final Attraction attraction;
    @JsonCreator
    public TrackerResponse(VisitedLocation visitedLocation, Attraction attraction) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
    }
}
