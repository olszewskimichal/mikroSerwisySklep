package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.product.ProductDTO;
import pl.michal.olszewski.product.ProductService;
import pl.michal.olszewski.product.ProductServiceImpl;
import pl.michal.olszewski.product.ProductStatus;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest {

    private ProductService service;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        initMocks(this);
        service = new ProductServiceImpl(restTemplate);
    }

    @Test
    public void shouldCorrectFilterProducts() {
        //given
        List<ProductDTO> products = Arrays.asList(
                ProductDTO.builder().productStatus(ProductStatus.IN_STORE.getValue()).build(),
                ProductDTO.builder().productStatus(ProductStatus.NEW.getValue()).build(),
                ProductDTO.builder().productStatus(ProductStatus.IN_WAREHOUSE.getValue()).build(),
                ProductDTO.builder().productStatus(ProductStatus.IN_COMPLAINT.getValue()).build());
        given(restTemplate.getForObject("http://localhost:8080/api/v1/products/byIds/" + "1,2,3,4", ProductDTO[].class)).willReturn((ProductDTO[]) products.toArray());

        //when
        List<ProductDTO> productDTOS = service.getAvailibleProductsForWarehouseFromApi("1,2,3,4");
        //then
        assertThat(productDTOS.size()).isEqualTo(2);

    }
}
