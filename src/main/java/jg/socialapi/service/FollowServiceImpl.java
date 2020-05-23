package jg.socialapi.service;

import jg.socialapi.entity.User;
import jg.socialapi.exceptions.FollowYourselfException;
import jg.socialapi.exceptions.IsAlreadyFollowingException;
import jg.socialapi.exceptions.UserNotFoundException;
import jg.socialapi.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private final UserService userService;

    public FollowServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User follow(String followingName, String followedName) {

        checkTwoDifferentUsernames(followingName, followedName);

        User follower = userService.findUser(followingName).orElseGet(() -> userService.persistUser(followingName));
        Optional<User> followedOptional = userService.findUser(followedName);

        checkIfUserExists(followedOptional, followedName);
        User followed = followedOptional.get();
        checkIfIsAlreadyFollowing(follower, followed);

        userService.addToFollowingList(follower, followed);

        return userService.findUser(followingName).get();
    }

    private void checkIfIsAlreadyFollowing(User follower, User followed) {
        if (isAlreadyFollowing(follower, followed)) {
            throw new IsAlreadyFollowingException(ErrorMessages.ALREADY_FOLLOWING, followed.getName());
        }
    }

    private void checkIfUserExists(Optional<User> followedOptional, String followedName) {
        if (followedOptional.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_DOES_NOT_EXIST, followedName);
        }
    }

    private void checkTwoDifferentUsernames(String username, String userToFollow) {
        if (username.equals(userToFollow)) {
            throw new FollowYourselfException("You can not follow yourself");
        }
    }

    private boolean isAlreadyFollowing(User follower, User followed) {
        return follower.getFollowed().contains(followed);
    }
}
