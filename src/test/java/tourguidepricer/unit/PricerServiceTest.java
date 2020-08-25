package tourguidepricer.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourguidepricer.domain.Provider;
import tourguidepricer.domain.TrackerResponse;
import tourguidepricer.domain.TripPricerTask;
import tourguidepricer.domain.UserReward;
import tourguidepricer.domain.location.Attraction;
import tourguidepricer.domain.location.Location;
import tourguidepricer.domain.location.VisitedLocation;
import tourguidepricer.service.PricerService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class PricerServiceTest {


    @InjectMocks
    PricerService pricerService = new PricerService();
    TrackerResponse trackerResponse;

    @BeforeEach
    void setup() {
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(2.0,1.0), new Date());
        Attraction attraction =new Attraction("manege", "utoya", "sweden", 2.0, 1.0);
        trackerResponse = new TrackerResponse(visitedLocation, attraction);
    }

    @Test
    public void generateUserReward() {
        //ARRANGE

        //ACT
        UserReward userReward = pricerService.generateUserReward(trackerResponse);

        //ASSERT
        assertThat(userReward.visitedLocation.location.latitude).isEqualTo(2.0);
        assertThat(userReward.visitedLocation.location.longitude).isEqualTo(1.0);
        assertThat(userReward.attraction).isEqualTo(trackerResponse.attraction);
    }

    @Test
    public void calculateAttractionRewards() {
        //ACT
        Integer rewards = pricerService.getAttractionRewardPointsByName("newAttraction");

        //ASSERT
        assertThat(rewards).isBetween(0,1000);
    }

    @Test
    public void getProviderWithPrice() {
        TripPricerTask tripPricerTask = new TripPricerTask("api",UUID.randomUUID(),1,3,1);

        //ACT
        List<Provider> providers = pricerService.getProviderWithPrice(tripPricerTask,101);

        //ASSERT
        assertThat(providers.get(0).price).isBetween(200.0,3000.0);
    }

    @Test
    public void getProviderWithPriceFreeWhenLotOfReward() {
        TripPricerTask tripPricerTask = new TripPricerTask("api",UUID.randomUUID(),1,1,1);

        //ACT
        List<Provider> providers = pricerService.getProviderWithPrice(tripPricerTask,10000);

        //ASSERT
        assertThat(providers.get(0).price).isEqualTo(0);
    }
}
