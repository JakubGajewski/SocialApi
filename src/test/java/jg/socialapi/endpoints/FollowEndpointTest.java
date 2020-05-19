package jg.socialapi.endpoints;

import jg.socialapi.controller.FollowController;
import jg.socialapi.entity.User;
import jg.socialapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static jg.socialapi.Constants.SAMPLE_FOLLOWED_USER_NAME;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static jg.socialapi.Constants.USER_HEADER;
import static jg.socialapi.util.TestUtils.assertCorrectHttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
public class FollowEndpointTest {

    @Autowired
    private FollowController followController;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    private final String followUrl = "/api/v1/follow/" + SAMPLE_FOLLOWED_USER_NAME;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(followController)
                .build();
    }

    @Test
    @DirtiesContext
    public void whenSendingFollowRequestTheUserShouldBePersistedInFollowingList() throws Exception {
        //Given
        persistMockedData();

        //When
        mockMvc.perform(put(followUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .accept(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.OK);
                    assertUserIsInFollowingList();
                });
    }

    private void persistMockedData() {
        userService.getUser(SAMPLE_USER_NAME);
        userService.getUser(SAMPLE_FOLLOWED_USER_NAME);
    }

    private void assertUserIsInFollowingList() {
        User followingUser = userService.getUser(SAMPLE_USER_NAME);
        User followedUser = userService.getUser(SAMPLE_FOLLOWED_USER_NAME);
        assertThat(followingUser.getFollowed().get(0).getName()).isEqualTo(followedUser.getName());
    }
}


