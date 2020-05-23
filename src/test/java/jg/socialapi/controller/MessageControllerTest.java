package jg.socialapi.controller;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import jg.socialapi.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import static jg.socialapi.Constants.SAMPLE_MESSAGE_JSON;
import static jg.socialapi.Constants.SAMPLE_MESSAGE_VALUE;
import static jg.socialapi.Constants.SAMPLE_TIMESTAMP;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static jg.socialapi.Constants.USER_HEADER;
import static jg.socialapi.util.ErrorMessages.CONTENT_TYPE_NOT_SUPPORTED;
import static jg.socialapi.util.ErrorMessages.METHOD_NOT_SUPPORTED;
import static jg.socialapi.util.TestUtils.assertCorrectHttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class MessageControllerTest {

    @Autowired
    MessageController messageController;

    @Mock
    MessageService messageService;

    private MockMvc mockMvc;

    private final String messageUrl = "/api/v1/message";

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(messageController)
                .build();
    }

    @Test
    void whenSendingMessagePostRequestThenShouldInvokeServiceAndReturnPersistedMessage() throws Exception {
        //given
        User sampleUser = new User(SAMPLE_USER_NAME).setId(1);
        Message sampleMessage = new Message(SAMPLE_MESSAGE_VALUE, sampleUser).setId(2).setTimestamp(Timestamp.valueOf(SAMPLE_TIMESTAMP));
        //when
        when(messageService.upsertUserWithMessage(SAMPLE_USER_NAME, SAMPLE_MESSAGE_VALUE)).thenReturn(new Message(SAMPLE_MESSAGE_VALUE, sampleUser));
        mockMvc.perform(post(messageUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .content(SAMPLE_MESSAGE_JSON)
                .accept(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.CREATED);
                    assertCorrectBody(result);
                });
    }

    private static void assertCorrectBody(MvcResult result) throws UnsupportedEncodingException {
        assertThat(result.getResponse().getContentAsString()).contains(SAMPLE_MESSAGE_VALUE);
    }

    @Test
    void whenSendingMessageGetRequestThenShouldReturnException() throws Exception {
        //when
        mockMvc.perform(get(messageUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .content(SAMPLE_MESSAGE_JSON)
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.METHOD_NOT_ALLOWED);
                    assertCorrectErrorMessage(result, METHOD_NOT_SUPPORTED);
                });
    }

    private static void assertCorrectErrorMessage(MvcResult result, String s) {
        assertThat(result.getResponse().getErrorMessage()).contains(s);
    }

    @Test
    void whenSendingPlainTextThenShouldReturnException() throws Exception {
        //when
        mockMvc.perform(post(messageUrl)
                .accept(MediaType.TEXT_PLAIN)
                .header(USER_HEADER, SAMPLE_USER_NAME)
                .content(SAMPLE_MESSAGE_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(result -> {
                    assertCorrectHttpStatus(result, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                    assertCorrectResolvedException(result, CONTENT_TYPE_NOT_SUPPORTED);
                });
    }

    private static void assertCorrectResolvedException(MvcResult result, String s) {
        assertThat(result.getResolvedException().getMessage()).contains(s);
    }
}
