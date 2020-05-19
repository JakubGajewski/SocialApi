package jg.socialapi.service;

import jg.socialapi.entity.Message;
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
        follower.getFollowed().add(followed);
        userRepository.save(follower);
    }

    @Override
    public User getUser(String username) {
        Optional<User> userOptional = userRepository.findByName(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return userRepository.save(createNewUser(username));
    }

    private User createNewUser(String username) {
        return new User()
                    .setName(username)
                    .setFollowed(new ArrayList<User>(){})
                    .setMessages(new ArrayList<Message>(){});
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
