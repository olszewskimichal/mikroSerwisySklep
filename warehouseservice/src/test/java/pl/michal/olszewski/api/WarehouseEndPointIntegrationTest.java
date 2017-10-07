package pl.michal.olszewski.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.builders.WarehouseDTOListFactory;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.service.WarehouseService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WarehouseEndPoint.class)
public class WarehouseEndPointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseService service;

    @Test
    public void shouldReturnWarehouseDTO() throws Exception {
        given(service.getWarehouseById(1L)).willReturn(WarehouseDTO.builder().name("nazwaTest").build());

        mockMvc.perform(get("/api/v1/warehouses/1"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnWarehouseDTO() throws Exception {
        given(service.getWarehouseById(1L)).willReturn(null);
        mockMvc.perform(get("/api/v1/warehouses/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnWarehouseDTOByName() throws Exception {
        given(service.getWarehouseByName("nazwaTest")).willReturn(WarehouseDTO.builder().name("nazwaTest").build());

        mockMvc.perform(get("/api/v1/warehouses/name/nazwaTest"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnWarehouseDTOByName() throws Exception {
        given(service.getWarehouseByName("nazwaTest")).willReturn(null);
        mockMvc.perform(get("/api/v1/warehouses/name/nazwaTest"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnWarehousesWithLimit() throws Exception {
        given(service.getWarehouses(2, null)).willReturn(WarehouseDTOListFactory.getNotPersistedWarehouses(2));
        mockMvc.perform(get("/api/v1/warehouses?limit=2"))
                .andExpect(content().string("[{\"name\":\"warehouse_0\",\"street\":\"street\",\"state\":null,\"city\":\"city\",\"country\":\"pl\",\"zipCode\":\"zip\"},{\"name\":\"warehouse_1\",\"street\":\"street\",\"state\":null,\"city\":\"city\",\"country\":\"pl\",\"zipCode\":\"zip\"}]"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldUpdateWarehouseAndReturn204() throws Exception {
        mockMvc.perform(put("/api/v1/warehouses/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateWarehouseAndReturn204() throws Exception {
        mockMvc.perform(post("/api/v1/warehouses").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"nazwaTest\",\"street\":null,\"state\":null,\"city\":null,\"country\":null,\"zipCode\":null}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteWarehouseAndReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/warehouses/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}