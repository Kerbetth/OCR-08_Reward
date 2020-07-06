package tourGuideTracker.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuideTracker.domain.UserReward;
import tourGuideTracker.domain.location.Attraction;
import tourGuideTracker.service.RewardsService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class TrackerServiceTest {


    @InjectMocks
    RewardsService rewardsService = new RewardsService();

    @BeforeEach
    void setup() {


    }

    @Test
    public void generateUserReward() {
        //ARRANGE
        Attraction attraction =new Attraction("manege", "utoya", "sweden", 2.0, 1.0);

        //ACT
        UserReward userReward = rewardsService.generateUserReward(
                attraction,
                UUID.randomUUID(),
                2.0,
                1.0);

        //ASSERT
        assertThat(userReward.visitedLocation.location.latitude).isEqualTo(1.0);
        assertThat(userReward.visitedLocation.location.longitude).isEqualTo(2.0);
        assertThat(userReward.attraction).isEqualTo(attraction);
    }

    @Test
    public void getLocationOfAllUsersShouldReturnGoodUserLocations() {
        Attraction attraction =new Attraction("manege", "utoya", "sweden", 2.0, 1.0);

        //ACT
        UserReward userReward = rewardsService.generateUserReward(
                attraction,
                UUID.randomUUID(),
                2.0,
                1.0);

        //ASSERT
        assertThat(userReward.visitedLocation.location.latitude).isEqualTo(1.0);
        assertThat(userReward.visitedLocation.location.longitude).isEqualTo(2.0);
        assertThat(userReward.attraction).isEqualTo(attraction);
    }

}
