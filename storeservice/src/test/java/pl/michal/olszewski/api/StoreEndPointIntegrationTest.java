package pl.michal.olszewski.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.builders.StoreDTOListFactory;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.service.StoreService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StoreEndPoint.class)
public class StoreEndPointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService service;

    @Test
    public void shouldReturnStoreDTO() throws Exception {
        given(service.getStoreById(1L)).willReturn(StoreDTO.builder().name("nazwaTest").build());

        mockMvc.perform(get("/api/v1/stores/1"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnStoreDTO() throws Exception {
        given(service.getStoreById(1L)).willReturn(null);
        mockMvc.perform(get("/api/v1/stores/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnStoreDTOByName() throws Exception {
        given(service.getStoreByName("nazwaTest")).willReturn(StoreDTO.builder().name("nazwaTest").build());

        mockMvc.perform(get("/api/v1/stores/name/nazwaTest"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnStoreDTOByName() throws Exception {
        given(service.getStoreByName("nazwaTest")).willReturn(null);
        mockMvc.perform(get("/api/v1/stores/name/nazwaTest"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnStoresWithLimit() throws Exception {
        given(service.getStores(2, null)).willReturn(StoreDTOListFactory.getNotPersistedStores(2));
        mockMvc.perform(get("/api/v1/stores?limit=2"))
                .andExpect(content().string("[{\"name\":\"store_0\",\"street\":\"street\",\"state\":null,\"city\":\"city\",\"country\":\"pl\",\"zipCode\":\"zip\"},{\"name\":\"store_1\",\"street\":\"street\",\"state\":null,\"city\":\"city\",\"country\":\"pl\",\"zipCode\":\"zip\"}]"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldUpdateStoreAndReturn204() throws Exception {
        mockMvc.perform(put("/api/v1/stores/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateStoreAndReturn204() throws Exception {
        mockMvc.perform(post("/api/v1/stores").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteStoreAndReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/stores/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}