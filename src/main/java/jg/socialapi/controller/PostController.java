package jg.socialapi.controller;

import jg.socialapi.entity.Post;
import jg.socialapi.service.PostService;
import jg.socialapi.service.UserService;
import jg.socialapi.util.ControllerHelper;
import jg.socialapi.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "/post", produces = "application/json")
    public ResponseEntity<Post> createPost(@RequestHeader("username") String username, @RequestBody String postMessage){ //TODO check if is <=140 chars

        if (isMessageTooLong(postMessage)) {
            return new ResponseEntity(Messages.MESSAGE_TOO_LONG, ControllerHelper.applicationJsonHeaders(), HttpStatus.BAD_REQUEST);
        }

        Post post = postService.createPost(username, postMessage);
        //TODO check 140 chars
        //return new ResponseEntity<>(MessageDto, ControllerHelper.applicationJsonHeaders(), HttpStatus.CREATED);
        return new ResponseEntity<>(post, ControllerHelper.applicationJsonHeaders(), HttpStatus.CREATED); //TODO another status if message too long
    }

    private boolean isMessageTooLong(String message) {
        return message.length() > 140 ? true : false;
    }

}
