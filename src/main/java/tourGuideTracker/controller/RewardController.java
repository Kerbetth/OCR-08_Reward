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
import tourGuideTracker.service.RewardsService;

@RestController
public class RewardController {

    @Autowired
    RewardsService rewardsService;

    @RequestMapping("/calculateRewards")
    public Integer calculateRewards(@RequestParam Set<UUID> attractionsId, @RequestParam UUID userId) {
        return rewardsService.calculateRewards(attractionsId, userId);
    }

    @RequestMapping("/calculateAttractionRewards")
    public Integer calculateAttractionRewards(@RequestParam Set<String> attractionsName) {
        return rewardsService.calculateAttractionRewards(attractionsName);
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestBody TripPricerTask tripPricerTask, @RequestParam double rewards) {
        return rewardsService.getProviderWithPrice(tripPricerTask, rewards);
    }
}