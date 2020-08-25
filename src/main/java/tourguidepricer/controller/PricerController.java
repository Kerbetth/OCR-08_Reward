package tourguidepricer.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourguidepricer.domain.Provider;
import tourguidepricer.domain.TripPricerTask;
import tourguidepricer.domain.UserReward;
import tourguidepricer.domain.TrackerResponse;
import tourguidepricer.service.PricerService;

@RestController
public class PricerController {

    @Autowired
    PricerService pricerService;

    @RequestMapping("/generateUserReward")
    public UserReward generateUserReward(@RequestBody TrackerResponse trackerResponse) {
        return pricerService.generateUserReward(trackerResponse);
    }
    
    @RequestMapping("/getAttractionRewardPoints")
    public int calculateAttractionRewards(@RequestParam String attractionName) {
        System.out.println(attractionName);
        return pricerService.getAttractionRewardPointsByName(attractionName);
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestBody TripPricerTask tripPricerTask, @RequestParam double rewards) {
        return pricerService.getProviderWithPrice(tripPricerTask, rewards);
    }
}