package tourguidepricer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import tourguidepricer.domain.location.Attraction;
import tourguidepricer.domain.location.VisitedLocation;

public class TrackerResponse {
    public final VisitedLocation visitedLocation;
    public final Attraction attraction;
    @JsonCreator
    public TrackerResponse(VisitedLocation visitedLocation, Attraction attraction) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
    }
}
