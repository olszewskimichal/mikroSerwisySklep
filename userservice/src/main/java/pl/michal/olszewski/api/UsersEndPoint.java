package pl.michal.olszewski.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.service.UserService;

@RestController
public class UsersEndPoint {
    private final UserService userService;

    public UsersEndPoint(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserDTO> getUser(long l) {
        return null;
    }

    public ResponseEntity<UserDTO> getUserByName(String userName2) {
        return null;
    }
}
