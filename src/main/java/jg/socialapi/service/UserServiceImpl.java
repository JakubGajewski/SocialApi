package jg.socialapi.service;

import jg.socialapi.entity.User;
import jg.socialapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Optional<User> findUser(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User persistUser(String username) {
        return userRepository.save(new User(username));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
