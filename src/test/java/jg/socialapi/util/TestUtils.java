package jg.socialapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static jg.socialapi.Constants.USER_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestUtils {

    public static void assertCorrectHttpStatus(MvcResult result, HttpStatus httpStatus) {
        assertThat(result.getResponse().getStatus()).isEqualTo(httpStatus.value());
    }

}
