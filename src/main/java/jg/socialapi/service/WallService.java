package jg.socialapi.service;

import jg.socialapi.dto.MessageDto;

import java.util.Collection;

public interface WallService {

    Collection<MessageDto> getWall(String username);

}
