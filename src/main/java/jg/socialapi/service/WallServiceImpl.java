package jg.socialapi.service;

import jg.socialapi.dto.MessageDto;
import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import jg.socialapi.exceptions.UserNotFoundException;
import jg.socialapi.util.ErrorMessages;
import jg.socialapi.util.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WallServiceImpl implements WallService {

    private final UserService userService;

    @Autowired
    public WallServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public Collection<MessageDto> tryGetWall(String username) {
        Optional<User> user = userService.findUser(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND, username);
        }
        return getWall(user.get());

    }

    private Collection<MessageDto> getWall(User user) {
        return user
                .getMessages()
                .stream()
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .map(MessageMapper::mapMessageToDto)
                .collect(Collectors.toList());
    }
}
