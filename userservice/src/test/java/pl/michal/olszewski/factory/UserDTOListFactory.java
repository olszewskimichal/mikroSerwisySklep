package pl.michal.olszewski.factory;

import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.entity.User;
import pl.michal.olszewski.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UserDTOListFactory {

    private final UserRepository repository;

    public UserDTOListFactory(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> buildNumberOfUsersDTOAndSave(int numberOfUsers) {
        List<UserDTO> userDTOS = new ArrayList<>();
        IntStream.range(0, numberOfUsers).forEachOrdered(number -> {
            User user = User.builder().firstName(String.format("userName_%s", number)).lastName("lastName").username("name").build();
            repository.saveAndFlush(user);
            userDTOS.add(new UserDTO(user));
        });
        return userDTOS;
    }

    public List<User> buildNumberOfUsersAndSave(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        IntStream.range(0, numberOfUsers).forEachOrdered(number -> {
            User user = User.builder().firstName(String.format("userName_%s", number)).lastName("lastName").username("name").build();
            repository.saveAndFlush(user);
            users.add(user);
        });
        return users;
    }

    public static List<UserDTO> getNotPersistedUsers(int numberOfUsers) {
        List<UserDTO> userDTOS = new ArrayList<>();
        IntStream.range(0, numberOfUsers).forEachOrdered(number -> {
            User user = User.builder().firstName(String.format("userName_%s", number)).lastName("lastName").username("name").build();
            userDTOS.add(new UserDTO(user));
        });
        return userDTOS;
    }

}
