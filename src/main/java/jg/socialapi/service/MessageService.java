package jg.socialapi.service;

import jg.socialapi.entity.Message;

public interface MessageService {

    Message getMessage(String username, String message);

}
