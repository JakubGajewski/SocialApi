package jg.socialapi.controller;

import jg.socialapi.dto.MessageDto;
import jg.socialapi.service.TimelineServiceImpl;
import jg.socialapi.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/v1")
public class TimelineController {
    private final TimelineServiceImpl timelineService;

    @Autowired
    public TimelineController (TimelineServiceImpl timelineService) {
        this.timelineService = timelineService;
    }

    @GetMapping(value = "/timeline", produces = "application/json")
    public ResponseEntity<Collection<MessageDto>> timeline(@RequestHeader String username) {
        Collection<MessageDto> timeline = timelineService.tryGetTimeline(username);
        return new ResponseEntity<>(timeline, ControllerHelper.applicationJsonHeaders(), HttpStatus.OK);
    }

}
