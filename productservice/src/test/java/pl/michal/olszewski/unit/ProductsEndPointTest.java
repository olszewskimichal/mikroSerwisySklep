package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.service.ProductService;
import pl.michal.olszewski.api.ProductsEndPoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductsEndPointTest {
    private ProductsEndPoint endPoint;

    @Mock
    private ProductService service;

    @Before
    public void setUp() {
        initMocks(this);
        endPoint = new ProductsEndPoint(service);
    }

    @Test
    public void shouldReturnProductById() {
        given(service.getProduct(1L)).willReturn(ProductDTO.builder().productDefinition(1L).productStatus(ProductStatus.SOLD.getValue()).build());

        ResponseEntity<ProductDTO> product = endPoint.getProduct(1L);

        assertThat(product).isNotNull();
        assertThat(product.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(product.getBody()).isNotNull();
        assertThat(product.getBody().getProductDefinition()).isEqualTo(1L);
    }

}
