package jg.socialapi.exceptions;

public class IsAlreadyFollowingException extends RuntimeException {
    public IsAlreadyFollowingException(String errorMessage, String username) {
        super(errorMessage + username);
    }
}
