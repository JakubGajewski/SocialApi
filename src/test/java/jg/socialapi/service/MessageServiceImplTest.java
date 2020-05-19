package jg.socialapi.service;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import jg.socialapi.repository.MessageRepository;
import jg.socialapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MessageServiceImplTest {

    MessageRepository messageRepository = Mockito.mock(MessageRepository.class);
    UserService userService = Mockito.mock(UserService.class);

    final String username = "john";
    final String messageValue = "this is a message";

    @Test
    void whenGetMessageThenReturnRightMessage() {
        //given
        User user = mockUser(username);
        Message message = mockMessage();
        user.getMessages().add(message);
        message.setUser(user);
        //when
        when(messageRepository.save(message)).thenReturn(message);
        when(userService.getUser(username)).thenReturn(user);
        //then
        MessageService messageService = new MessageServiceImpl(messageRepository, userService);
        assertThat(messageService.getMessage(username, messageValue)).isEqualTo(message);
    }

    private User mockUser(String username) {
        User user = new User();
        user.setId(12);
        user.setName(username);
        user.setFollowed(new ArrayList());
        user.setMessages(new ArrayList());
        return user;
    }

    private Message mockMessage() {
        Message message = new Message();
        message.setId(13);
        message.setTimestamp(Timestamp.valueOf("2020-01-01 12:00:00"));
        message.setValue(messageValue);
        return message;
    }

}
