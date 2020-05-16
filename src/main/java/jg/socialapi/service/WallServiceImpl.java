package jg.socialapi.service;

import jg.socialapi.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WallServiceImpl implements WallService {

    private final PostService postService;

    @Autowired
    public WallServiceImpl(PostService postService) {
        this.postService = postService;
    }

    public String getWall(String userName) {
        System.out.println("Get wall service.getWall(" + userName + ");");
        Collection<Post> wallPosts = postService.getMessagesForUser(userName);
        System.out.println("wall posts " + wallPosts);
        StringBuilder response = new StringBuilder();
        //TODO na streamy i kolejność!
        for (Post wallPost : wallPosts) {
            response.append(wallPost);
        }
        System.out.println("response: " + response);
        return response.toString();

    }

}
