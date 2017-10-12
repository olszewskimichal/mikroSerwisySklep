package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public UserDTO(User user) {
    }
}
