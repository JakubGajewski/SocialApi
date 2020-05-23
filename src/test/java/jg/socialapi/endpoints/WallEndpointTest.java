package jg.socialapi.endpoints;

import jg.socialapi.controller.WallController;
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

import static jg.socialapi.Constants.SAMPLE_MESSAGE;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_1;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_VALUE;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_VALUE_1;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static jg.socialapi.Constants.USER_HEADER;
import static jg.socialapi.util.TestUtils.assertCorrectHttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class WallEndpointTest {

    @Autowired
    private WallController wallController;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    private final String wallUrl = "/api/v1/wall";

    @BeforeEach
    void setup() {
        setupStandaloneWallController();
    }

    private void setupStandaloneWallController() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wallController)
                .build();
    }

    @Test
    @DirtiesContext
    public void whenSendingValidGetRequestThenShouldReceiveWall() throws Exception {
        //Given
        persistMockedData();

        //When
        mockMvc.perform(get(wallUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .accept(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.OK);
                    assertRightMessagesAreReturned(result, SAMPLE_MESSAGE_VALUE, SAMPLE_MESSAGE_VALUE_1);
                });
    }

    public static void assertRightMessagesAreReturned(MvcResult result, String... expectedMessages) throws UnsupportedEncodingException {
        for (String message : expectedMessages) {
            assertThat(result.getResponse().getContentAsString()).contains(message);
        }
    }

    private void persistMockedData() {
        saveUser(jg.socialapi.Constants.SAMPLE_USER_NAME);
        messageService.getMessage(SAMPLE_USER_NAME, SAMPLE_MESSAGE);
        messageService.getMessage(SAMPLE_USER_NAME, SAMPLE_MESSAGE_1);
    }

    private User saveUser(String username) {
        User user = new User(username);
        return userService.saveUser(user);
    }
}
