package pl.michal.olszewski.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.JpaTestBase;
import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends JpaTestBase {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserById() {
        //given
        Long id = entityManager.persistAndFlush(new User(UserDTO.builder().username("user").firstName("first").lastName("last").email("email").build())).getId();
        //when
        Optional<User> user = userRepository.findById(id);
        //then
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUsername()).isEqualTo("user");
    }

    @Test
    public void shouldFindUserByName() {
        //given
        entityManager.persistAndFlush(new User(UserDTO.builder().username("user2").firstName("first").lastName("last").email("email").build())).getId();
        //when
        Optional<User> user = userRepository.findByUsername("user2");
        //then
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUsername()).isEqualTo("user2");
    }

    @Test
    public void shouldUpdateUser() {
        //given
        User user = entityManager.persistAndFlush(new User(UserDTO.builder().username("user").firstName("first").lastName("last").email("email").build()));
        //when
        userRepository.updateUser("user3", "first", "last", "email2", user.getId());
        entityManager.clear();
        //then
        User updatedUser = userRepository.findOne(user.getId());
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("user3");
        assertThat(updatedUser.getEmail()).isEqualTo("email2");
    }
}