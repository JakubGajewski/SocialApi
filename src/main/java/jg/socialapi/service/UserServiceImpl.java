package jg.socialapi.service;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import jg.socialapi.exceptions.UserAlreadyExistsException;
import jg.socialapi.exceptions.UserNotFoundException;
import jg.socialapi.repository.UserRepository;
import jg.socialapi.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.NoSuchElementException;
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
    public Optional<User> findUser(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User persistUser(String username) {
        return userRepository.save(new User(username));
    }

    @Override
    public User saveUser(User user) {
        checkIfAlreadyExists(user);
        return userRepository.save(user);
    }

    private void checkIfAlreadyExists(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, user.getName());
        }
    }

    @Override
    public User updateUser(User user) {
        if (userRepository.findByName(user.getName()).isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_DOES_NOT_EXIST, user.getName());
        }
        return userRepository.save(user);
    }

    @Override
    public Message getLastMessage(User user) {
        return user.getMessages().stream().max(Comparator.comparing(Message::getTimestamp)).orElseThrow(NoSuchElementException::new);
    }
}
