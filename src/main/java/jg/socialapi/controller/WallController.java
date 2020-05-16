package jg.socialapi.controller;

import jg.socialapi.entity.Post;
import jg.socialapi.service.PostService;
import jg.socialapi.service.WallService;
import jg.socialapi.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class WallController {
    private final WallService wallService;

    @Autowired
    public WallController(WallService wallService) {
        this.wallService = wallService;
    }

    @GetMapping(value = "/wall", produces = "application/json")
    public ResponseEntity<String> readWall(@RequestHeader String username) {
        String response = wallService.getWall(username);
        //TODO implement
        //TODO jsonify and order
        return new ResponseEntity(response, ControllerHelper.applicationJsonHeaders(), HttpStatus.OK); //TODO another status if message too long
    }
}
