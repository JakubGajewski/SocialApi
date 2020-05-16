package jg.socialapi.service;

import jg.socialapi.entity.Post;
import jg.socialapi.entity.User;
import jg.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addToFollowingList(User follower, User followed) {
        follower.getFollowing().add(followed);
        userRepository.save(follower);
    }

    @Override //TODO przerobić na ładne
    public User getUser(String username) {
        Optional<User> userOptional = userRepository.findByName(username);
        if (userOptional.isPresent()) {
            System.out.println("User is present!");
            return userOptional.get();
        }
        System.out.println("User is not present!");
        User user = new User();
        user.setName(username);
        user.setFollowing(new ArrayList<User>(){});
        user.setPosts(new ArrayList<Post>(){});
        userRepository.save(user);
        return user;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private User getUserIfExists(String username) {
        //TODO implement userRepository.findUserByName
        return null;
    }

    private User registerUser(String username) {
        //TODO implement
        return null;
    }
}
