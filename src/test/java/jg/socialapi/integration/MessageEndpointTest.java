package jg.socialapi.integration;

import jg.socialapi.controller.MessageController;
import jg.socialapi.repository.MessageRepository;
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

import static jg.socialapi.Constants.SAMPLE_MESSAGE_JSON;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_VALUE;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static jg.socialapi.Constants.USER_HEADER;
import static jg.socialapi.util.TestUtils.assertCorrectHttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class MessageEndpointTest {

    @Autowired
    private MessageController messageController;

    @Autowired
    private MessageRepository messageRepository;

    private MockMvc mockMvc;

    private final String messageUrl = "/api/v1/message";

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(messageController)
                .build();
    }

    @Test
    @DirtiesContext
    public void whenSendingValidPostRequestThenMessageShouldBePersisted() throws Exception {

        //When
        mockMvc.perform(post(messageUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .content(SAMPLE_MESSAGE_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.CREATED);
                    assertCorrectResponse(result);
                    assertMessageIsPersisted();
                });
    }

    private static void assertCorrectResponse(MvcResult result) throws UnsupportedEncodingException {
        assertThat(result.getResponse().getContentAsString()).contains(SAMPLE_MESSAGE_VALUE);
    }

    private void assertMessageIsPersisted() {
        assertThat(messageRepository.findAll().iterator().next().getValue()).contains(SAMPLE_MESSAGE_VALUE);
    }
}


