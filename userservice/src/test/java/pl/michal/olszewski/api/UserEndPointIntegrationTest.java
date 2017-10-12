package pl.michal.olszewski.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.dto.UserDTO;
import pl.michal.olszewski.factory.UserDTOListFactory;
import pl.michal.olszewski.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UsersEndPoint.class)
public class UserEndPointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void shouldReturnUserDTO() throws Exception {
        given(service.getUserById(1L)).willReturn(UserDTO.builder().username("nazwaTest").build());

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnUserDTO() throws Exception {
        given(service.getUserById(1L)).willReturn(null);
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUserDTOByName() throws Exception {
        given(service.getUserByName("nazwaTest")).willReturn(UserDTO.builder().username("nazwaTest").build());

        mockMvc.perform(get("/api/v1/users/name/nazwaTest"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnUserDTOByName() throws Exception {
        given(service.getUserByName("nazwaTest")).willReturn(null);
        mockMvc.perform(get("/api/v1/users/name/nazwaTest"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUsersWithLimit() throws Exception {
        given(service.getUsers(2, null)).willReturn(UserDTOListFactory.getNotPersistedUsers(2));
        mockMvc.perform(get("/api/v1/users?limit=2"))
                .andExpect(content().string("[{\"name\":\"user_0\",\"street\":\"street\",\"state\":null,\"city\":\"city\",\"country\":\"pl\",\"zipCode\":\"zip\"},{\"name\":\"user_1\",\"street\":\"street\",\"state\":null,\"city\":\"city\",\"country\":\"pl\",\"zipCode\":\"zip\"}]"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldUpdateUserAndReturn204() throws Exception {
        mockMvc.perform(put("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateUserAndReturn204() throws Exception {
        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteUserAndReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}