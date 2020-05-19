package jg.socialapi.repository;

import jg.socialapi.entity.Message;
import jg.socialapi.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    Collection<Message> findByUser(User user);

}
