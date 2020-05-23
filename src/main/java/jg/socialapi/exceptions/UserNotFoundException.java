package jg.socialapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errorMessage, String username) {
        super(errorMessage + username);
    }
}
