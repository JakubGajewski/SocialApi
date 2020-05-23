package jg.socialapi.service;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Optional;

import static jg.socialapi.Constants.SAMPLE_TIMESTAMP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MessageServiceImplTest {

    UserService userService = Mockito.mock(UserService.class);

    final String username = "john";
    final String messageValue = "this is a message";

    @Test
    void whenGetMessageThenReturnRightMessage() {
        //given
        User user = mockUserWithMessage();
        //when
        when(userService.findUser(username)).thenReturn(Optional.of(user));
        when(userService.updateUser(user)).thenReturn(user);
        when(userService.getLastMessage(user)).thenReturn(user.getMessages().get(0));
        //then
        MessageService messageService = new MessageServiceImpl(userService);
        assertThat(messageService.upsertUserWithMessage(username, messageValue).getValue())
                .isEqualTo(userService.getLastMessage(user).getValue());
    }

    private User mockUserWithMessage() {
        User user = mockUser();
        Message message = mockMessage(user);
        message.setUser(user);
        user.getMessages().add(message);
        return user;
    }

    private User mockUser() {
        User user = new User(username);
        user.setId(12);
        return user;
    }

    private Message mockMessage(User user) {
        Message message = new Message(messageValue, user);
        message.setId(13);
        message.setTimestamp(Timestamp.valueOf(SAMPLE_TIMESTAMP));
        return message;
    }

}
