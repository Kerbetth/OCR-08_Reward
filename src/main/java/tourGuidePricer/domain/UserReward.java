package tourGuidePricer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import tourGuidePricer.domain.location.Attraction;
import tourGuidePricer.domain.location.VisitedLocation;

public class UserReward {

    public final VisitedLocation visitedLocation;
    public final Attraction attraction;
    private int rewardPoints;

    @JsonCreator
    public UserReward(@JsonProperty("visitedLocation") VisitedLocation visitedLocation,
                      @JsonProperty("attraction") Attraction attraction,
                      @JsonProperty("rewardPoints") int rewardPoints) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
        this.rewardPoints = rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

}
