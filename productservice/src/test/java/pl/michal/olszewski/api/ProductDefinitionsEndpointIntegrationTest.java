package pl.michal.olszewski.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.builders.ProductDefinitionsDTOListFactory;
import pl.michal.olszewski.dto.ProductDefinitionDTO;
import pl.michal.olszewski.enums.ProductType;
import pl.michal.olszewski.service.ProductService;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductsDefinitionEndPoint.class, secure = false)
public class ProductDefinitionsEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void shouldReturnProductDefinitionDTO() throws Exception {
        given(service.getProductDefinition(1L)).willReturn(ProductDefinitionDTO.builder().name("nazwaTest").prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP)).build());

        mockMvc.perform(get("/api/v1/productDefinitions/1"))
                .andExpect(content().string("{\"name\":\"nazwaTest\",\"description\":null,\"imageUrl\":null,\"prodType\":1,\"price\":10.00}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldReturnProductsWithLimit() throws Exception {
        given(service.getProductDefinitions(2, null)).willReturn(ProductDefinitionsDTOListFactory.getNotPersistedProductDef(2));
        mockMvc.perform(get("/api/v1/productDefinitions?limit=2"))
                .andExpect(content().string("[{\"name\":\"product_0\",\"description\":null,\"imageUrl\":null,\"prodType\":1,\"price\":10.00},{\"name\":\"product_1\",\"description\":null,\"imageUrl\":null,\"prodType\":1,\"price\":10.00}]"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnProductDefinitionDTO() throws Exception {
        given(service.getProductDefinition(1L)).willReturn(null);
        mockMvc.perform(get("/api/v1/productDefinitions/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldUpdateProductAndReturn204() throws Exception {
        mockMvc.perform(put("/api/v1/productDefinitions/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1,\"productDefinition\":1,\"productStatus\":5}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateProductAndReturn204() throws Exception {
        mockMvc.perform(post("/api/v1/productDefinitions").contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":null,\"productDefinition\":1,\"productStatus\":4}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteProductAndReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/productDefinitions/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
