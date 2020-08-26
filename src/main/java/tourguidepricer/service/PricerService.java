package tourguidepricer.service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourguidepricer.domain.Provider;
import tourguidepricer.domain.TripPricerTask;
import tourguidepricer.domain.UserReward;
import tourguidepricer.domain.TrackerResponse;
import tourguidepricer.domain.location.VisitedLocation;
import tourguidepricer.util.ProviderUtil;


@Service
public class PricerService {

    @Autowired
    ProviderUtil providerUtil;

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

    public int getAttractionRewardPointsByName(String attractionsName) {
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
                    providerUtil.getProviderName(tripPricerTask.getApiKey(), tripPricerTask.getAdults()),
                    price));
        }
        return providers;
    }

}
