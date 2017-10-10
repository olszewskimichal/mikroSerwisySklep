package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.repository.ProductDefinitionRepository;
import pl.michal.olszewski.repository.ProductRepository;
import pl.michal.olszewski.service.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest {
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDefinitionRepository productDefinitionRepository;

    @Before
    public void setUp() {
        initMocks(this);
        productService = new ProductService(productRepository, productDefinitionRepository);
    }

    @Test
    public void shouldReturnAvailableProductsForIds() {
        //given
        ProductDefinition definition = ProductDefinition.builder().name("aaa").id(1L).prodType(1L).price(BigDecimal.ONE).build();
        given(productRepository.findByProductIds(Arrays.asList(1L, 2L, 3L))).willReturn(Arrays.asList(new Product(definition, ProductStatus.IN_STORE), new Product(definition, ProductStatus.IN_WAREHOUSE)));
        //when
        List<ProductDTO> availableProducts = productService.getAvailableProducts("1,2,3");
        //then
        assertThat(availableProducts.size()).isEqualTo(2);
    }

    @Test
    public void shouldChangeProductStatus() {
        //given
        ProductDefinition definition = ProductDefinition.builder().name("aaa").id(1L).prodType(1L).price(BigDecimal.ONE).build();
        given(productRepository.findByProductIds(Arrays.asList(1L, 2L, 3L))).willReturn(Arrays.asList(new Product(definition, ProductStatus.IN_STORE), new Product(definition, ProductStatus.IN_WAREHOUSE)));
        //when
        List<ProductDTO> products = productService.changeProductsStatus(ProductsStatusChangeDTO.builder().productsId(Arrays.asList(1L, 2L, 3L)).productStatus(ProductStatus.NEW.getValue()).build());
        //then
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).getProductStatus()).isEqualTo(ProductStatus.NEW.getValue());
        assertThat(products.get(1).getProductStatus()).isEqualTo(ProductStatus.NEW.getValue());
    }

    @Test
    public void shouldReturnNullWhenProductNotExist() {
        given(productRepository.findByIdFetchProductDetails(anyLong())).willReturn(null);

        ProductDTO product = productService.getProduct(1L);

        assertThat(product).isNull();
    }
}
