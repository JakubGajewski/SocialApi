package jg.socialapi.controller;

import jg.socialapi.entity.User;
import jg.socialapi.service.UserService;
import jg.socialapi.util.ControllerHelper;
import jg.socialapi.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class FollowController {

    private final UserService userService;

    @Autowired
    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/follow/{userToFollow}", produces = "application/json")
    public ResponseEntity follow(@PathVariable String userToFollow, @RequestHeader("username") String username) {
        //TODO refactor

        if (isSelfFollowing(userToFollow, username)) {
            return new ResponseEntity(Messages.FOLLOWING_YOURSELF, ControllerHelper.applicationJsonHeaders(), HttpStatus.BAD_REQUEST);
        }

        System.out.println("PUT request on follow");
        User follower = userService.getUser(username);
        System.out.println("Follower: " + follower);
        User followed = userService.getUser(userToFollow);
        System.out.println("Followed: " + followed);

        if (isAlreadyFollowing(follower, followed)) {
            return new ResponseEntity(Messages.USER_ALREADY_IN_FOLLOWING_LIST, ControllerHelper.applicationJsonHeaders(), HttpStatus.BAD_REQUEST); //TODO another status if message too long
        }

        userService.addToFollowingList(follower, followed);
        return new ResponseEntity(follower, ControllerHelper.applicationJsonHeaders(), HttpStatus.CREATED); //TODO another status if message too long
    }

    private boolean isSelfFollowing(String followerName, String followingName) {
        return followerName.equals(followingName)? true : false;
    }

    private boolean isAlreadyFollowing(User follower, User followed) {
        return follower.getFollowing().contains(followed) ? true : false;
    }
}
