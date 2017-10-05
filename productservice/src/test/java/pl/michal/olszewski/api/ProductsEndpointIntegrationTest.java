package pl.michal.olszewski.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.builders.ProductDTOListFactory;
import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.service.ProductService;
import pl.michal.olszewski.service.ProductsEndPoint;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductsEndPoint.class, secure = false)
public class ProductsEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void shouldReturnProductDTO() throws Exception {
        given(service.getProduct(1L)).willReturn(ProductDTO.builder().productId(1L).productDefinition(1L).productStatus(ProductStatus.SOLD.getValue()).build());

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(content().string("{\"productId\":1,\"productDefinition\":1,\"productStatus\":6}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldReturnProductsWithLimit() throws Exception {
        given(service.getProducts(2, null)).willReturn(ProductDTOListFactory.getNotPersistedProducts(2, new ProductDefinition(), ProductStatus.IN_STORE));
        mockMvc.perform(get("/api/v1/products?limit=2"))
                .andExpect(content().string("[{\"productId\":null,\"productDefinition\":null,\"productStatus\":3},{\"productId\":null,\"productDefinition\":null,\"productStatus\":3}]"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotReturnProductDTO() throws Exception {
        given(service.getProduct(1L)).willReturn(null);
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldUpdateProductAndReturn204() throws Exception {
        mockMvc.perform(put("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1,\"productDefinition\":1,\"productStatus\":5}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateProductAndReturn204() throws Exception {
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":null,\"productDefinition\":1,\"productStatus\":4}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteProductAndReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
