package jg.socialapi.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ControllerHelper {

    public static HttpHeaders applicationJsonHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
