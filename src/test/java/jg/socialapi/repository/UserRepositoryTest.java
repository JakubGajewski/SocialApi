package jg.socialapi.repository;

import jg.socialapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Optional;

import static jg.socialapi.Constants.SAMPLE_FOLLOWED_USER_NAME;
import static jg.socialapi.Constants.SAMPLE_USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void whenSaveTheUserThenShouldBeAbleToQueryUserByName() {
        //given
        User user = new User(SAMPLE_USER_NAME);
        User followed = new User(SAMPLE_FOLLOWED_USER_NAME);
        user.getFollowed().add(followed);

        //when
        userRepository.save(user);
        Optional<User> queriedUser = userRepository.findByName(SAMPLE_USER_NAME);

        //then
        assertThat(queriedUser.isPresent()).isTrue();
        assertThat(queriedUser.get().getId()).isNotNull();
        assertThat(queriedUser.get().getFollowed()).isInstanceOf(Collection.class);
        assertThat(queriedUser.get().getFollowed().get(0).getName()).isEqualTo(followed.getName());
        assertThat(queriedUser.get().getMessages()).isInstanceOf(Collection.class);
        assertThat(queriedUser.get().getMessages()).isEmpty();
    }
}
