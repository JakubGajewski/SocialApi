package jg.socialapi.controller;

import jg.socialapi.entity.User;
import jg.socialapi.exceptions.FollowYourselfException;
import jg.socialapi.exceptions.UserNotFoundException;
import jg.socialapi.service.UserService;
import jg.socialapi.util.ControllerHelper;
import jg.socialapi.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class FollowController {

    private final UserService userService;

    @Autowired
    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/follow/{userToFollow}", produces = "application/json")
    public ResponseEntity follow(@PathVariable String userToFollow, @RequestHeader("username") String username) throws FollowYourselfException {

        selfFollowingCheck(userToFollow, username);
        User follower = userService.findUser(username).orElse(userService.persistUser(username));
        Optional<User> followed = userService.findUser(userToFollow);

        if(followed.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND, userToFollow);
        };

        if (isAlreadyFollowing(follower, followed.get())) {
            return new ResponseEntity(ErrorMessages.USER_ALREADY_IN_FOLLOWING_LIST, ControllerHelper.applicationJsonHeaders(), HttpStatus.BAD_REQUEST);
        }

        userService.addToFollowingList(follower, followed.get());
        return new ResponseEntity(follower, ControllerHelper.applicationJsonHeaders(), HttpStatus.OK);
    }

    private void selfFollowingCheck(@PathVariable String userToFollow, @RequestHeader("username") String username) {
        if (userToFollow.equals(username)) {
            throw new FollowYourselfException("You can not follow yourself");
        }
    }

    private boolean isAlreadyFollowing(User follower, User followed) {
        return follower.getFollowed().contains(followed);
    }
}
