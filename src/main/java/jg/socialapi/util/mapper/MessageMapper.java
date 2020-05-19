package jg.socialapi.util.mapper;

import jg.socialapi.dto.MessageDto;
import jg.socialapi.entity.Message;

public class MessageMapper {

    public static MessageDto mapMessageToDto (Message message) {
        return new MessageDto()
                .setDateTime(message.getTimestamp().toString())
                .setValue(message.getValue())
                .setUsername(message.getUser().getName());
    }

}
