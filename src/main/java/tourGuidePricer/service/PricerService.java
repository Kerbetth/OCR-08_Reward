package tourGuidePricer.service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import tourGuidePricer.domain.Provider;
import tourGuidePricer.domain.TripPricerTask;
import tourGuidePricer.domain.UserReward;
import tourGuidePricer.domain.TrackerResponse;
import tourGuidePricer.domain.location.VisitedLocation;


@Service
public class PricerService {

    private static final String tripPricerApiKey = "test-server-api-key";

    public List<Provider> getTripDeals(TripPricerTask tripPricerTask, double cumulatativeRewardPoints) {
        List<Provider> providers = getProviderWithPrice(tripPricerTask, cumulatativeRewardPoints);
        return providers;
    }

    public UserReward generateUserReward(TrackerResponse trackerResponse) {
        return new UserReward(
                new VisitedLocation(
                        trackerResponse.visitedLocation.userId,
                        trackerResponse.visitedLocation.location,
                        new Date()),
                trackerResponse.attraction,
                getAttractionRewardPoints(trackerResponse.attraction.attractionId));
    }

    public Integer calculateAttractionRewards(Set<String> attractionsName) {
        Integer allpoints = 0;
        for (String attraction : attractionsName) {
            allpoints += getAttractionRewardPointsByName(attraction);
        }
        return allpoints;
    }


    private int getAttractionRewardPointsByName(String attraction) {
        //TODO this method need to be completed in order to get a coherent amount of rewardPoint
        return ThreadLocalRandom.current().nextInt(1, 1000);
    }

    private int getAttractionRewardPoints(UUID attraction) {
        //TODO this method need to be completed in order to get a coherent amount of rewardPoint
        return ThreadLocalRandom.current().nextInt(1, 1000);
    }

    public List<Provider> getProviderWithPrice(TripPricerTask tripPricerTask, double rewards) {
        List<Provider> providers = new ArrayList();
        for (int i = 0; i < 5; ++i) {
            int multiple = ThreadLocalRandom.current().nextInt(100, 700);
            double childrenDiscount = (tripPricerTask.getChildren() / 3);
            double price =
                    (double) (multiple * tripPricerTask.getAdults())
                    + (double) multiple * childrenDiscount
                            * (double) tripPricerTask.getNightsStay()
                            + 0.99D
                            - rewards;
            if (price < 0.0D) {
                price = 0.0D;
            }

            providers.add(new Provider(
                    tripPricerTask.getAttractionId(),
                    getProviderName(tripPricerTask.getApiKey(), tripPricerTask.getAdults()),
                    price));
        }

        return providers;
    }

    public String getProviderName(String apiKey, int adults) {
        int multiple = ThreadLocalRandom.current().nextInt(1, 10);
        switch (multiple) {
            case 1:
                return "Holiday Travels";
            case 2:
                return "Enterprize Ventures Limited";
            case 3:
                return "Sunny Days";
            case 4:
                return "FlyAway Trips";
            case 5:
                return "United Partners Vacations";
            case 6:
                return "Dream Trips";
            case 7:
                return "Live Free";
            case 8:
                return "Dancing Waves Cruselines and Partners";
            case 9:
                return "AdventureCo";
            default:
                return "Cure-Your-Blues";
        }
    }
}
