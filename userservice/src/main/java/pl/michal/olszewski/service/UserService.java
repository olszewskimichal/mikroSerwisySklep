package pl.michal.olszewski.service;

import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.entity.User;
import pl.michal.olszewski.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final int PAGE_LIMIT = 20;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable("users")
    public UserDTO getUserById(final Long userId) {
        return userRepository.findById(userId).map(UserDTO::new)
                .orElse(null);
    }

    @Cacheable("users")
    public UserDTO getUserByName(@NonNull final String userName) {
        return userRepository.findByUsername(userName).map(UserDTO::new)
                .orElse(null);
    }

    @Cacheable("users")
    public List<UserDTO> getUsers(final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest(getPage(page), getLimit(limit));
        return userRepository.findAll(pageRequest).getContent().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @CacheEvict(value = "users", allEntries = true)
    public void createUser(final UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void updateUser(final UserDTO userDTO, final Long id) {
        userRepository.updateUser(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(final Long id) {
        userRepository.delete(id);
    }

    private int getLimit(final Integer size) {
        return (Objects.isNull(size) ? PAGE_LIMIT : size);
    }

    private int getPage(final Integer page) {
        return (Objects.isNull(page) ? 0 : page);
    }
}
