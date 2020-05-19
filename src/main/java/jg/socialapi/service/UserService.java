package jg.socialapi.service;

import jg.socialapi.entity.User;

public interface UserService {
    User getUser(String username);
    User saveUser(User user);
    void addToFollowingList(User follower, User followed);
}
