package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.michal.olszewski.entity.User;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
