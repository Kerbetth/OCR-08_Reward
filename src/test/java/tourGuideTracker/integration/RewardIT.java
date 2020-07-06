package tourGuideTracker.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import tourGuideTracker.domain.UserReward;
import tourGuideTracker.domain.location.Attraction;
import tourGuideTracker.service.RewardsService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
public class RewardIT {

    @Autowired
    RewardsService rewardsService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    protected MockMvc mvc;


    @Test
    public void shouldReturnUserRewardGenerateUserReward() throws Exception {
        //ARRANGE
        Attraction attraction = new Attraction("manege", "utoya", "sweden", 2.0, 1.0);
        UUID uuid = UUID.randomUUID();
        ObjectMapper postMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = postMapper
                    .writeValueAsString(attraction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //ACT
        UserReward userRewardResult = objectMapper.readValue(
                mvc.perform(post("/generateUserReward")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", uuid.toString())
                        .param("latitude", "2.0")
                        .param("longitude", "1.0")
                        .content(requestBody)
                )
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(), UserReward.class);
        //ASSERT
        assertThat(userRewardResult.attraction.attractionName).isEqualTo(attraction.attractionName);
        assertThat(userRewardResult.visitedLocation.userId).isEqualTo(uuid);
        assertThat(userRewardResult.visitedLocation.location.latitude).isEqualTo(1.0);
        assertThat(userRewardResult.visitedLocation.location.longitude).isEqualTo(2.0);
    }


    @Test
    public void shouldReturnCumulativeAttractionRewardPoints() throws Exception {
        //ACT
        String result =
                mvc.perform(get("/getCumulativeAttractionRewardPoints?attractionsName=attraction1,attraction2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        //ASSERT
        assertThat(Integer.parseInt(result)).isBetween(3,3000);
    }
}
