package pl.michal.olszewski.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.entity.User;
import pl.michal.olszewski.factory.UserDTOListFactory;
import pl.michal.olszewski.factory.UserListAssert;
import pl.michal.olszewski.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersApiTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void should_get_empty_list_of_users() {
        givenUser()
                .buildNumberOfUsersDTOAndSave(0);

        List<UserDTO> users = thenGetUsersFromApi();

        assertThat(users).isEmpty();
    }

    @Test
    public void should_get_all_users() {
        givenUser()
                .buildNumberOfUsersDTOAndSave(3);

        List<UserDTO> users = thenGetUsersFromApi();

        assertThat(users).hasSize(3);
    }

    @Test
    public void should_get_limit_three_users() {
        givenUser()
                .buildNumberOfUsersDTOAndSave(6);

        List<UserDTO> userDefinitionDTOS = thenGetNumberUsersFromApi(3);

        UserListAssert.assertThat(userDefinitionDTOS)
                .isSuccessful()
                .hasNumberOfItems(3);
    }

    @Test
    public void should_get_one_user_byName() {
        List<UserDTO> userDTOS = givenUser()
                .buildNumberOfUsersDTOAndSave(1);

        UserDTO user = thenGetOneUserFromApiByUserName(userDTOS.get(0).getUsername());

        assertThat(userDTOS.get(0)).isEqualToComparingOnlyGivenFields(user, "name", "street", "city", "country", "zipCode");
    }

    @Test
    public void should_get_one_user() {
        List<User> users = givenUser()
                .buildNumberOfUsersAndSave(1);

        UserDTO userDTO = thenGetOneUserFromApiById(users.get(0).getId());
        assertThat(new UserDTO(users.get(0))).isEqualToComparingOnlyGivenFields(userDTO, "name", "street", "city", "country", "zipCode");
    }

    @Test
    public void should_create_a_user() {
        //given
        userRepository.deleteAll();
        //when
        thenCreateUserByApi("test");

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(userRepository.findAll().get(0)).isNotNull();
    }

    @Test
    public void should_update_existing_user() {
        //given
        User user = givenUser()
                .buildNumberOfUsersAndSave(1).get(0);
        UserDTO userDTO = new UserDTO(user);

        //when
        thenUpdateUserByApi(user.getId(), userDTO);

        //then
        assertThat(userRepository.findByUserName(userDTO.getUsername()))
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "nazwa nowa");
    }

    @Test
    public void should_delete_existing_user() {
        //given
        User user = givenUser()
                .buildNumberOfUsersAndSave(1).get(0);
        //when
        thenDeleteOneUserFromApi(user.getId());

        //then
        assertThat(userRepository.findOne(user.getId())).isNull();
    }

    private UserDTOListFactory givenUser() {
        return new UserDTOListFactory(userRepository);
    }

    private List<UserDTO> thenGetUsersFromApi() {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/users", port), UserDTO[].class).getBody());
    }

    private UserDTO thenGetOneUserFromApiById(Long id) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/users/%s", port, id), UserDTO.class).getBody();
    }

    private UserDTO thenGetOneUserFromApiByUserName(String name) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/users/name/%s", port, name), UserDTO.class).getBody();
    }

    private List<UserDTO> thenGetNumberUsersFromApi(int number) {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/users?limit=%s", port, number), UserDTO[].class).getBody());
    }

    private void thenCreateUserByApi(String name) {
        template.postForEntity(String.format("http://localhost:%s/api/v1/users", port), UserDTO.builder().username(name).build(), UserDTO.class);
    }

    private void thenUpdateUserByApi(Long userId, UserDTO userDTO) {
        template.put(String.format("http://localhost:%s/api/v1/users/%s", port, userId), userDTO);
    }

    private void thenDeleteOneUserFromApi(Long userId) {
        template.delete(String.format("http://localhost:%s/api/v1/users/%s", port, userId));
    }

}
