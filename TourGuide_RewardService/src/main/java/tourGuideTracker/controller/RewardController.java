package tourGuideTracker.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuideTracker.service.RewardsService;

@RestController
public class RewardController {

    @Autowired
    RewardsService rewardsService;

/*
    @RequestMapping("/getReward")
    public String getReward(@RequestParam String userName, @RequestParam String attractionName) {
        return rewardsService.calculateRewards(userName);
    }*/

    @RequestMapping("/calculateRewards")
    public Integer calculateRewards(@RequestParam Set<UUID> attractionsId, @RequestParam UUID userId) {
        System.out.println(attractionsId+ "  "+userId);
        return rewardsService.calculateRewards(attractionsId, userId);
    }

    @RequestMapping("/calculateAttractionRewards")
    public Integer calculateAttractionRewards(@RequestParam Set<String> attractionsName) {
        return rewardsService.calculateAttractionRewards(attractionsName);
    }

}