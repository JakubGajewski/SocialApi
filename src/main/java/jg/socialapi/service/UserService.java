package jg.socialapi.service;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;

import java.util.Optional;

public interface UserService {
    User persistUser(String username);
    Optional<User> findUser(String username);
    User saveUser(User user);
    User updateUser(User user);
    void addToFollowingList(User follower, User followed);
    Message getLastMessage(User user);
}
