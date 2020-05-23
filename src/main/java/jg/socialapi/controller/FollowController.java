package jg.socialapi.controller;

import jg.socialapi.entity.User;
import jg.socialapi.service.FollowService;
import jg.socialapi.service.UserService;
import jg.socialapi.util.ControllerHelper;
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
    private final FollowService followService;
    private final UserService userService;

    @Autowired
    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @PutMapping(value = "/follow/{userToFollow}", produces = "application/json")
    public ResponseEntity follow(@RequestHeader("username") String username, @PathVariable String userToFollow) {
        followService.follow(username, userToFollow);
        User follower = userService.findUser(username).get();
        return new ResponseEntity(follower, ControllerHelper.applicationJsonHeaders(), HttpStatus.OK);
    }
}
