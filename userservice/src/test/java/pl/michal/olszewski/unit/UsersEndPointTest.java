package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.api.UsersEndPoint;
import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class UsersEndPointTest {
    private UsersEndPoint endPoint;

    @Mock
    private UserService service;

    @Before
    public void setUp() {
        initMocks(this);
        endPoint = new UsersEndPoint(service);
    }

    @Test
    public void shouldReturnUserById() {
        given(service.getUserById(1L)).willReturn(UserDTO.builder().username("userName").firstName("nazwa").email("email").build());

        ResponseEntity<UserDTO> user = endPoint.getUser(1L);

        assertThat(user).isNotNull();
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        assertThat(user.getBody().getUsername()).isEqualTo("nazwa");
    }

    @Test
    public void shouldNotReturnUserByIdWhenNotExist() {
        given(service.getUserById(1L)).willReturn(null);

        ResponseEntity<UserDTO> user = endPoint.getUser(1L);

        assertThat(user).isNotNull();
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(user.getBody()).isNull();
    }

    @Test
    public void shouldReturnUserByName() {
        given(service.getUserByName("nazwa2")).willReturn(UserDTO.builder().username("userName2").firstName("nazwa").email("email").build());

        ResponseEntity<UserDTO> user = endPoint.getUserByName("userName2");

        assertThat(user).isNotNull();
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        assertThat(user.getBody().getUsername()).isEqualTo("nazwa2");
    }

    @Test
    public void shouldNotReturnUserByNameWhenNotExist() {
        given(service.getUserByName("nazwa2")).willReturn(null);

        ResponseEntity<UserDTO> user = endPoint.getUserByName("nazwa2");

        assertThat(user).isNotNull();
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(user.getBody()).isNull();
    }

}
