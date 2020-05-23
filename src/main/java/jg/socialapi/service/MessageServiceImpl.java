package jg.socialapi.service;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import jg.socialapi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public Message getMessage(String username, String messageValue) {
        User user = userService.findUser(username).orElse(userService.persistUser(username));
        Message message = messageRepository.save(new Message(messageValue, user));
        user.getMessages().add(message);
        userService.updateUser(user);
        return message;
    }

}
