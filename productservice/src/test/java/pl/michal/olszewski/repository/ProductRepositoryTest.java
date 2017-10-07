package pl.michal.olszewski.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.JpaTestBase;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.enums.ProductType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends JpaTestBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDefinitionRepository productDefinitionRepository;

    private ProductDefinition productDefinition;

    @Before
    public void setUp() {
        productRepository.deleteAll();
        productDefinitionRepository.deleteAll();
        productDefinition = productDefinitionRepository.save(ProductDefinition.builder().name("nazwa").prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP)).build());
    }

    @Test
    public void shouldFindProductAndFetchDefinitions() {
        //given
        Product product = this.entityManager.persistAndFlush(new Product(productDefinition, ProductStatus.IN_STORE));
        //when
        Product byIdFetchProductDetails = this.productRepository.findByIdFetchProductDetails(product.getId());
        //then
        assertThat(byIdFetchProductDetails).isNotNull();
        assertThat(byIdFetchProductDetails.getProductDefinition()).isEqualTo(productDefinition);
        assertThat(byIdFetchProductDetails.getProductStatus()).isEqualTo(ProductStatus.IN_STORE.getValue());
    }

    @Test
    public void shouldUpdateProduct() {
        //given
        Product product = this.entityManager.persistAndFlush(new Product(productDefinition, ProductStatus.IN_STORE));
        //when
        this.productRepository.updateProduct(productDefinition, ProductStatus.SOLD.getValue(), product.getId());
        //then
        entityManager.clear();
        Product updatedProduct = productRepository.findOne(product.getId());
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getProductDefinition()).isEqualTo(productDefinition);
        assertThat(updatedProduct.getProductStatus()).isEqualTo(ProductStatus.SOLD.getValue());
    }

    @Test
    public void shouldGetPageableProductsWithLimit() {
        //given
        IntStream.range(0, 10).forEach(v -> this.entityManager.persistAndFlush(new Product(productDefinition, ProductStatus.IN_STORE)));
        assertThat(productRepository.findAll().size()).isEqualTo(10);
        //when
        Page<Product> products = productRepository.findAll(new PageRequest(0, 5));
        //then
        assertThat(products.getTotalElements()).isEqualTo(10);
        assertThat(products.getNumberOfElements()).isEqualTo(5);
        assertThat(products.getTotalPages()).isEqualTo(2);
        assertThat(products.getContent().size()).isEqualTo(5);
    }

    @Test
    public void shouldGetProductsByIds() {
        //given
        IntStream.range(0, 5).forEach(v -> this.entityManager.persistAndFlush(new Product(productDefinition, ProductStatus.IN_STORE)));
        List<Product> products = productRepository.findAll();
        //when
        List<Product> productByIds = productRepository.findByProductIds(products.stream().map(Product::getId).collect(Collectors.toList()));
        //then
        assertThat(products).isEqualTo(productByIds);
    }
}