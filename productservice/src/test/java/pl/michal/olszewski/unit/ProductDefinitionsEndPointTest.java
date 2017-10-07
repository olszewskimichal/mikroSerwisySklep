package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.api.ProductsDefinitionEndPoint;
import pl.michal.olszewski.dto.ProductDefinitionDTO;
import pl.michal.olszewski.service.ProductService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductDefinitionsEndPointTest {
    private ProductsDefinitionEndPoint endPoint;

    @Mock
    private ProductService service;

    @Before
    public void setUp() {
        initMocks(this);
        endPoint = new ProductsDefinitionEndPoint(service);
    }

    @Test
    public void shouldReturnProductById() {
        given(service.getProductDefinition(1L)).willReturn(ProductDefinitionDTO.builder().name("nazwa").price(BigDecimal.TEN).build());

        ResponseEntity<ProductDefinitionDTO> productDefinition = endPoint.getProductDefinition(1L);

        assertThat(productDefinition).isNotNull();
        assertThat(productDefinition.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productDefinition.getBody()).isNotNull();
        assertThat(productDefinition.getBody().getName()).isEqualTo("nazwa");
        assertThat(productDefinition.getBody().getPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldReturnProductByName() {
        given(service.getProductDefinitionByName("nazwa3")).willReturn(ProductDefinitionDTO.builder().name("nazwa3").price(BigDecimal.TEN).build());

        ResponseEntity<ProductDefinitionDTO> productDefinition = endPoint.getProductDefinitionByName("nazwa3");

        assertThat(productDefinition).isNotNull();
        assertThat(productDefinition.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productDefinition.getBody().getName()).isEqualTo("nazwa3");
    }

}
