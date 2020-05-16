package jg.socialapi.service;

import jg.socialapi.entity.Post;
import jg.socialapi.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface PostService {
    public Post createPost(String username, String message);

    Collection<Post> getMessagesForUser(String username);
}
