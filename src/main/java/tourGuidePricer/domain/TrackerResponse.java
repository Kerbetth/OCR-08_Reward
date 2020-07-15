package tourGuidePricer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import tourGuidePricer.domain.location.Attraction;
import tourGuidePricer.domain.location.VisitedLocation;

public class TrackerResponse {
    public final VisitedLocation visitedLocation;
    public final Attraction attraction;
    @JsonCreator
    public TrackerResponse(VisitedLocation visitedLocation, Attraction attraction) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
    }
}
