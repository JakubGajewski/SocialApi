package jg.socialapi.repository;

import jg.socialapi.entity.Post;
import jg.socialapi.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    //public Collection<Post> getByUserId(Integer userId);
    //List<WashComment> findByWash_CarWash_Id($Parameter(name="id") int id)
    public List<Post> findByUserId(Integer id);
}
