package jg.socialapi.service;

import jg.socialapi.entity.User;
import jg.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    public User getUser(String username);
    public User saveUser(User user);
    public void addToFollowingList(User follower, User followed);

}
