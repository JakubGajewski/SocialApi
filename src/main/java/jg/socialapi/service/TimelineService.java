package jg.socialapi.service;

import jg.socialapi.dto.MessageDto;

import java.util.Collection;

public interface TimelineService {
    Collection<MessageDto> tryGetTimeline(String username);
}
