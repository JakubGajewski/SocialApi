package jg.socialapi.service;

import jg.socialapi.entity.Post;
import jg.socialapi.entity.User;
import jg.socialapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserService userService;

    @Autowired
    public PostServiceImpl (PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Post createPost(String username, String message) {
//        //TODO implement relation
        User user = userService.getUser(username);
        Post post = new Post(); //TODO create valid post
        post.setMessage(message);
        postRepository.save(post);
        user.getPosts().add(post);
        userService.saveUser(user);

        return post;
    }

    @Override
    public Collection<Post> getMessagesForUser(String username) {
        User user = userService.getUser(username);
        System.out.println("User in PostService: " + user);
        return postRepository.findByUserId(user.getId()); //TODO what if not exists?
    }
}
