package jg.socialapi.controller;

import jg.socialapi.dto.MessageDto;
import jg.socialapi.entity.Message;
import jg.socialapi.service.MessageService;
import jg.socialapi.util.ControllerHelper;
import jg.socialapi.util.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = "/message", produces = "application/json")
    public ResponseEntity<MessageDto> createPost(@RequestHeader("username") String username, @Valid @RequestBody MessageDto messageDto){
        Message persistedMessage = messageService.upsertUserWithMessage(username, messageDto.getValue());
        MessageDto updatedMessageDto = MessageMapper.mapMessageToDto(persistedMessage);
        return new ResponseEntity<>(updatedMessageDto, ControllerHelper.applicationJsonHeaders(), HttpStatus.CREATED);
    }

}
