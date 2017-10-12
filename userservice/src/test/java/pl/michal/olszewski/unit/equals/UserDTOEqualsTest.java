package pl.michal.olszewski.unit.equals;

import pl.michal.olszewski.dto.UserDTO;

public class UserDTOEqualsTest extends LocalEqualsHashCodeTest<UserDTO> {
    @Override
    protected UserDTO createInstance() {
        return UserDTO.builder().username("name").firstName("first").lastName("last").email("email").build();
    }

    @Override
    protected UserDTO createNotEqualInstance() {
        return UserDTO.builder().username("name2").firstName("first").lastName("last").email("email").build();    }
}
