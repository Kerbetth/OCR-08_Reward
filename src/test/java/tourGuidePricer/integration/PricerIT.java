package tourGuidePricer.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import tourGuidePricer.domain.Provider;
import tourGuidePricer.domain.TrackerResponse;
import tourGuidePricer.domain.TripPricerTask;
import tourGuidePricer.domain.UserReward;
import tourGuidePricer.domain.location.Attraction;
import tourGuidePricer.domain.location.Location;
import tourGuidePricer.domain.location.VisitedLocation;
import tourGuidePricer.service.PricerService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class PricerIT {

    @Autowired
    PricerService pricerService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    protected MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();
    TrackerResponse trackerResponse;

    @BeforeEach
    void setup() {
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(2.0,1.0), new Date());
        Attraction attraction =new Attraction("manege", "utoya", "sweden", 2.0, 1.0);
        trackerResponse = new TrackerResponse(visitedLocation, attraction);
    }

    @Test
    public void shouldReturnUserRewardGenerateUserReward() throws Exception {
        //ARRANGE
        Set<String> attactions = new HashSet<>();
        String rewards =
                mvc.perform(post("/getCumulativeAttractionRewardPoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("attractionsName", attactions.toString())
                )
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        //ASSERT
        assertThat(Integer.parseInt(rewards)).isBetween(1,1000);
    }


    @Test
    public void shouldReturnTripDeals() throws Exception {
        //ACT
        TripPricerTask tripPricerTask = new TripPricerTask("api",UUID.randomUUID(),1,1,1);
        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(tripPricerTask);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Provider> providers = objectMapper.readValue(
                mvc.perform(get("/getTripDeals?rewards=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(), List.class);

        //ASSERT
        assertThat(providers).hasSize(5);
    }

    @Test
    public void shouldReturnTripDealsWithFreePrice() throws Exception {
        //ACT
        TripPricerTask tripPricerTask = new TripPricerTask("api",UUID.randomUUID(),1,1,1);
        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(tripPricerTask);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Provider> providers = objectMapper.readValue(
                mvc.perform(get("/getTripDeals?rewards=10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(), List.class);

        //ASSERT
        assertThat(providers).hasSize(5);
    }

    @Test
    public void generateUserReward() throws Exception {
        //ACT

        String requestBody = null;
        try {
            requestBody = objectMapper
                    .writeValueAsString(trackerResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        UserReward userReward = objectMapper.readValue(
                mvc.perform(get("/generateUserReward")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(), UserReward.class);

        //ASSERT
        assertThat(userReward.attraction.attractionName).isEqualTo("manege");
        assertThat(userReward.visitedLocation.location.latitude).isEqualTo(2.0);
        assertThat(userReward.visitedLocation.location.longitude).isEqualTo(1.0);
    }
}
