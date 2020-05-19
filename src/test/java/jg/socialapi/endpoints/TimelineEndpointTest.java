package jg.socialapi.endpoints;

import jg.socialapi.controller.TimelineController;
import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import jg.socialapi.service.MessageService;
import jg.socialapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static jg.socialapi.Constants.SAMPLE_FOLLOWED_USER_NAME;
import static jg.socialapi.Constants.SAMPLE_MESSAGE;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_1;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_VALUE_1;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static jg.socialapi.Constants.USER_HEADER;
import static jg.socialapi.util.TestUtils.assertCorrectHttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class TimelineEndpointTest {

    @Autowired
    private TimelineController timelineController;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    private final String timelineUrl = "/api/v1/timeline";

    @BeforeEach
    void setup() {
        setupStandaloneWallController();
    }

    private void setupStandaloneWallController() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(timelineController)
                .build();
    }

    @Test
    @DirtiesContext
    public void whenSendingValidGetRequestThenShouldReceiveTimeline() throws Exception {
        //Given
        persistMockedData();

        //When
        mockMvc.perform(get(timelineUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .accept(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.OK);
                    assertRightMessagesAreReturned(result, SAMPLE_MESSAGE_VALUE_1, SAMPLE_MESSAGE_VALUE_1);
                });
    }

    public static void assertRightMessagesAreReturned(MvcResult result, String... expectedMessages) throws UnsupportedEncodingException {
        for (String message : expectedMessages) {
            assertThat(result.getResponse().getContentAsString()).contains(message);
        }
    }

    private void persistMockedData() {
        User followingUser = saveUser(SAMPLE_USER_NAME);
        User followedUser = saveUser(SAMPLE_FOLLOWED_USER_NAME);
        userService.addToFollowingList(followingUser, followedUser);
        messageService.getMessage(SAMPLE_FOLLOWED_USER_NAME, SAMPLE_MESSAGE);
        messageService.getMessage(SAMPLE_FOLLOWED_USER_NAME, SAMPLE_MESSAGE_1);
    }

    private User saveUser(String username) {
        User user = new User();
        user.setName(username)
                .setFollowed(new ArrayList<User>())
                .setMessages(new ArrayList<Message>());
        return userService.saveUser(user);
    }
}
