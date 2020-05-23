package jg.socialapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String errorMessage, String username) {
        super(errorMessage + username);
    }
}
