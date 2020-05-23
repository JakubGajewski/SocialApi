package jg.socialapi.service;

import jg.socialapi.entity.Message;

public interface MessageService {
    Message upsertUserWithMessage(String username, String messageValue);
}
