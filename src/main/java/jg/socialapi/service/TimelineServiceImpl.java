package jg.socialapi.service;

import jg.socialapi.dto.MessageDto;
import jg.socialapi.entity.Message;
import jg.socialapi.util.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class TimelineServiceImpl implements TimelineService {

    private final UserService userService;

    @Autowired
    TimelineServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Collection<MessageDto> getTimeline(String username) {
        return userService.getUser(username)
                        .getFollowed()
                        .stream()
                        .flatMap(user -> user.getMessages().stream())
                        .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                        .map(MessageMapper::mapMessageToDto)
                        .collect(Collectors.toList());
    }

}
