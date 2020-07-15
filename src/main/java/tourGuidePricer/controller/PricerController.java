package tourGuidePricer.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuidePricer.domain.Provider;
import tourGuidePricer.domain.TripPricerTask;
import tourGuidePricer.domain.UserReward;
import tourGuidePricer.domain.TrackerResponse;
import tourGuidePricer.service.PricerService;

@RestController
public class PricerController {

    @Autowired
    PricerService pricerService;

    @RequestMapping("/generateUserReward")
    public UserReward generateUserReward(@RequestBody TrackerResponse trackerResponse) {
        return pricerService.generateUserReward(trackerResponse);
    }
    
    @RequestMapping("/getCumulativeAttractionRewardPoints")
    public int calculateAttractionRewards(@RequestParam Set<String> attractionsName) {
        return pricerService.calculateAttractionRewards(attractionsName);
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestBody TripPricerTask tripPricerTask, @RequestParam double rewards) {
        return pricerService.getProviderWithPrice(tripPricerTask, rewards);
    }
}