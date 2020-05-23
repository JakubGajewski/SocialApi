package jg.socialapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static void assertCorrectHttpStatus(MvcResult result, HttpStatus httpStatus) {
        assertThat(result.getResponse().getStatus()).isEqualTo(httpStatus.value());
    }

}
