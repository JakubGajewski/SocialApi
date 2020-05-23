package jg.socialapi.service;


import jg.socialapi.entity.User;

public interface FollowService {
    User follow(String username, String userToFollow);
}
