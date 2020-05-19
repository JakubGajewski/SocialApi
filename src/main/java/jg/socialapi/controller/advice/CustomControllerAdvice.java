package jg.socialapi.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception exception) {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorCommunicate errorCommunicate = new ErrorCommunicate(exception);
        String response = "{}";
        try {
            response = objectMapper.writeValueAsString(errorCommunicate);
        } catch (JsonProcessingException jsonParsingException) {
            jsonParsingException.printStackTrace();
        }
        return ResponseEntity.badRequest().body(response);
    }

    private class ErrorCommunicate {
        public final String className;
        public final String exceptionMessage;

        ErrorCommunicate(Exception exception) {
            this.className = exception.getClass().getName();
            this.exceptionMessage = exception.getLocalizedMessage();
        }
    }

}
