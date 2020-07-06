package tourGuideTracker.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuideTracker.domain.Provider;
import tourGuideTracker.domain.TripPricerTask;
import tourGuideTracker.domain.UserReward;
import tourGuideTracker.domain.location.Attraction;
import tourGuideTracker.domain.location.VisitedLocation;
import tourGuideTracker.service.RewardsService;

@RestController
public class RewardController {

    @Autowired
    RewardsService rewardsService;

    @RequestMapping("/generateUserReward")
    public UserReward generateUserReward(@RequestBody Attraction attraction,
                                         @RequestParam UUID userId,
                                         @RequestParam double longitude,
                                         @RequestParam double latitude) {
        return rewardsService.generateUserReward(attraction, userId, longitude, latitude);
    }
    
    @RequestMapping("/getCumulativeAttractionRewardPoints")
    public int calculateAttractionRewards(@RequestParam Set<String> attractionsName) {
        return rewardsService.calculateAttractionRewards(attractionsName);
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestBody TripPricerTask tripPricerTask, @RequestParam double rewards) {
        return rewardsService.getProviderWithPrice(tripPricerTask, rewards);
    }
}