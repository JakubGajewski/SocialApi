package jg.socialapi.service;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final UserService userService;

    @Autowired
    public MessageServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Message upsertUserWithMessage(String username, String messageValue) {
        Optional<User> userOptional = userService.findUser(username);

        if (userOptional.isPresent()) {
            return updateUserWithNewMessage(messageValue, userOptional.get());
        }
        User user = createNewUserWithMessage(username, messageValue);
        return userService.getLastMessage(user);
    }

    private User createNewUserWithMessage(String username, String messageValue) {
        User user = new User(username);
        Message message = new Message(messageValue, user);
        user.getMessages().add(message);
        return userService.saveUser(user);
    }

    private Message updateUserWithNewMessage(String messageValue, User user) {
        Message message = new Message(messageValue, user);
        user.getMessages().add(message);
        user = userService.updateUser(user);
        return userService.getLastMessage(user);
    }

}
