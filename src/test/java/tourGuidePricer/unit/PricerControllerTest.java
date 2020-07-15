package tourGuidePricer.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tourGuidePricer.controller.PricerController;
import tourGuidePricer.domain.TrackerResponse;
import tourGuidePricer.domain.TripPricerTask;
import tourGuidePricer.domain.location.Attraction;
import tourGuidePricer.domain.location.Location;
import tourGuidePricer.domain.location.VisitedLocation;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PricerControllerTest {

    @MockBean
    private PricerController pricerController;

    @Autowired
    protected MockMvc mockMvc;
    ObjectMapper postMapper = new ObjectMapper();
    TrackerResponse trackerResponse;

    @BeforeEach
    void setup() {
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(2.0, 1.0), new Date());
        Attraction attraction = new Attraction("manege", "utoya", "sweden", 2.0, 1.0);
        trackerResponse = new TrackerResponse(visitedLocation, attraction);
    }

    @Test
    public void getLocation() throws Exception {
        String request = null;
        try {
            request = postMapper.writeValueAsString(trackerResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.mockMvc.perform(post("/generateUserReward")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void get5NearestAttractions() throws Exception {
        Set<String> attactions = new HashSet<>();

        this.mockMvc.perform(post("/getCumulativeAttractionRewardPoints?attractionsName="+attactions.toString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getCurrentLocationOfAllUsers() throws Exception {
        TripPricerTask tripPricerTask = new TripPricerTask("api",UUID.randomUUID(),1,1,1);
        String requestBody = null;
        try {
            requestBody = postMapper
                    .writeValueAsString(tripPricerTask);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.mockMvc.perform(post("/getTripDeals?rewards=20")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isOk());
    }

}
