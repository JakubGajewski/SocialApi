package jg.socialapi.util.mapper;

import jg.socialapi.dto.MessageDto;
import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static jg.socialapi.Constants.SAMPLE_MESSAGE_VALUE;
import static jg.socialapi.Constants.SAMPLE_TIMESTAMP;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class MessageMapperTest {

    @Test
    void mapMessageToDto() {
        //given
        Message message = new Message(SAMPLE_MESSAGE_VALUE);
        message.setId(3);
        message.setUser(new User(SAMPLE_USER_NAME));
        message.setTimestamp(Timestamp.valueOf(SAMPLE_TIMESTAMP));

        //when
        MessageDto dto = MessageMapper.mapMessageToDto(message);

        //then
        assertThat(dto.getValue()).isEqualTo(message.getValue());
        assertThat(Timestamp.valueOf(dto.getDateTime())).isEqualTo(message.getTimestamp());
        assertThat(dto.getUsername()).isEqualTo(message.getUser().getName());
    }
}
