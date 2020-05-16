package jg.socialapi.service;

import jg.socialapi.entity.Post;
import jg.socialapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimelineServiceImpl implements TimelineService {

    private final UserService userService;

    @Autowired
    TimelineServiceImpl(UserService userService) {
        this.userService = userService;
    }


    //TODO return JSON object instead of String
    public String getTimeline(String username) {
        List<User> followedUsers  = userService.getUser(username).getFollowing();
        //TODO change order to chronological
        Collection<Post> timeline = followedUsers.stream().flatMap(user -> user.getPosts().stream()).collect(Collectors.toList()); //.sorted() TODO
        StringBuilder response = new StringBuilder(); //TODO buuu!
        timeline.forEach(
                post -> {
                    response.append(post.getMessage());
                }
        );
        return response.toString();
    }
}
