package jg.socialapi.controller;

import jg.socialapi.entity.Post;
import jg.socialapi.service.TimelineService;
import jg.socialapi.service.TimelineServiceImpl;
import jg.socialapi.util.ControllerHelper;
import jg.socialapi.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class TimelineController {
    private final TimelineServiceImpl timelineService;

    @Autowired
    public TimelineController (TimelineServiceImpl timelineService) {
        this.timelineService = timelineService;
    }

    @GetMapping(value = "/timeline", produces = "application/json")
    public ResponseEntity<String> timeline(@RequestHeader String username) {
        String result = timelineService.getTimeline(username);
        //TODO NPE
        return new ResponseEntity<>(result, ControllerHelper.applicationJsonHeaders(), HttpStatus.CREATED); //TODO another status if message too long
    }

}
