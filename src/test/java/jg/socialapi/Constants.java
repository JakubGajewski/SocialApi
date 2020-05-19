package jg.socialapi;

public class Constants {
    public static final String USER_HEADER = "username";
    public static final String SAMPLE_USER_NAME = "john";
    public static final String SAMPLE_FOLLOWED_USER_NAME = "haruki";
    public static final String SAMPLE_MESSAGE_VALUE = "the message";
    public static final String SAMPLE_MESSAGE_VALUE_1 = "another message";

    public static final String SAMPLE_MESSAGE = String.format("{\n" +
            "  \"value\": \"%s\"\n" +
            "}", SAMPLE_MESSAGE_VALUE);

    public static final String SAMPLE_MESSAGE_1 = String.format("{\n" +
            "  \"value\": \"%s\"\n" +
            "}", SAMPLE_MESSAGE_VALUE_1);
}
