package pl.michal.olszewski.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.builders.ProductDTOListFactory;
import pl.michal.olszewski.builders.ProductListAssert;
import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.enums.ProductType;
import pl.michal.olszewski.repository.ProductDefinitionRepository;
import pl.michal.olszewski.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductsApiTest extends IntegrationTest {

    @Autowired
    ProductRepository realProductRepository;

    @Autowired
    ProductDefinitionRepository productDefinitionRepository;

    private ProductDefinition productDefinition;

    @Before
    public void setUp() {
        realProductRepository.deleteAll();
        productDefinitionRepository.deleteAll();
        productDefinition = productDefinitionRepository.save(ProductDefinition.builder().name("test").prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN).build());
    }

    @Test
    public void should_get_empty_list_of_products() {
        givenProduct()
                .buildNumberOfProductsAndSave(0, null, null);

        List<ProductDTO> products = thenGetProductsFromApi();

        assertThat(products).isEmpty();
    }

    @Test
    public void should_get_3_products() {
        givenProduct()
                .buildNumberOfProductsAndSave(3, productDefinition, ProductStatus.IN_COMPLAINT);

        List<ProductDTO> products = thenGetProductsFromApi();

        assertThat(products).hasSize(3);
    }

    @Test
    public void should_get_one_product() {
        List<ProductDTO> givenProducts = givenProduct()
                .buildNumberOfProductsAndSave(1, productDefinition, ProductStatus.SOLD);

        ProductDTO product = thenGetOneProductFromApi(givenProducts.get(0).getProductId());

        assertThat(givenProducts.get(0)).isEqualToComparingFieldByField(product);
    }

    @Test
    public void should_get_limit_three_products() {
        givenProduct()
                .buildNumberOfProductsAndSave(6, productDefinition, ProductStatus.IN_WAREHOUSE);

        List<ProductDTO> products = thenGetNumberProductsFromApi(3);

        ProductListAssert.assertThat(products)
                .isSuccessful()
                .hasNumberOfItems(3);
    }

    @Test
    public void should_create_a_product() {
        //given
        realProductRepository.deleteAll();
        //when
        thenCreateProductByApi(productDefinition.getId());

        //then
        assertThat(realProductRepository.findAll().size()).isEqualTo(1);
        assertThat(realProductRepository.findAll().get(0)).isNotNull();
    }

    @Test
    public void should_update_existing_product() {
        //given
        ProductDTO productDTO = givenProduct()
                .buildNumberOfProductsAndSave(1, productDefinition, ProductStatus.LIQUIDATION).get(0);
        productDTO.setProductStatus(ProductStatus.IN_STORE.getValue());

        //when
        thenUpdateProductByApi(productDTO.getProductId(), productDTO);

        //then
        assertThat(realProductRepository.findByIdFetchProductDetails(productDTO.getProductId()))
                .isNotNull()
                .hasFieldOrPropertyWithValue("productStatus", ProductStatus.IN_STORE.getValue());
    }

    @Test
    public void should_delete_existing_product() {
        //given
        ProductDTO productDTO = givenProduct()
                .buildNumberOfProductsAndSave(1, productDefinition, ProductStatus.IN_COMPLAINT).get(0);
        //when
        thenDeleteOneProductFromApi(productDTO.getProductId());

        //then
        assertThat(realProductRepository.findByIdFetchProductDetails(productDTO.getProductId())).isNull();
    }

    @Test
    public void should_get_available_products() {
        String productIds = givenProduct()
                .buildNumberOfProductsAndSave(6, productDefinition, ProductStatus.IN_WAREHOUSE).stream().map(ProductDTO::getProductId).map(Object::toString).collect(Collectors.joining(","));

        List<ProductDTO> products = thenGetAvailableProductsByIdsFromApi(productIds.substring(0, productIds.length() - 2));

        ProductListAssert.assertThat(products)
                .isSuccessful()
                .hasNumberOfItems(5);
    }

    @Test
    public void should_change_status_for_products() {
        List<Long> idList = givenProduct()
                .buildNumberOfProductsAndSave(6, productDefinition, ProductStatus.NEW).stream().map(ProductDTO::getProductId).collect(Collectors.toList());

        List<ProductDTO> products = thenChangeStatusForProductsIdsByAPI(idList,ProductStatus.IN_WAREHOUSE.getValue());

        ProductListAssert.assertThat(products)
                .isSuccessful()
                .hasNumberOfItems(6)
                .hasProductsStatus(ProductStatus.IN_WAREHOUSE.getValue());
    }

    private ProductDTOListFactory givenProduct() {
        return new ProductDTOListFactory(realProductRepository);
    }

    private List<ProductDTO> thenGetProductsFromApi() {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/products", port), ProductDTO[].class).getBody());
    }

    private ProductDTO thenGetOneProductFromApi(Long productId) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/products/%s", port, productId), ProductDTO.class).getBody();
    }

    private List<ProductDTO> thenGetNumberProductsFromApi(int number) {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/products?limit=%s", port, number), ProductDTO[].class).getBody());
    }

    private void thenCreateProductByApi(Long productDef) {
        template.postForEntity(String.format("http://localhost:%s/api/v1/products", port), ProductDTO.builder().productDefinition(productDef).productStatus(ProductStatus.IN_COMPLAINT.getValue()).build(), ProductDTO.class);
    }

    private void thenUpdateProductByApi(Long productId, ProductDTO productDTO) {
        template.put(String.format("http://localhost:%s/api/v1/products/%s", port, productId), productDTO);
    }

    private void thenDeleteOneProductFromApi(Long productId) {
        template.delete(String.format("http://localhost:%s/api/v1/products/%s", port, productId));
    }

    private List<ProductDTO> thenGetAvailableProductsByIdsFromApi(String ids) {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/products/byIds/%s", port, ids), ProductDTO[].class).getBody());
    }

    private List<ProductDTO> thenChangeStatusForProductsIdsByAPI(List<Long> ids, Long status) {
        return Arrays.asList(template.postForEntity(String.format("http://localhost:%s/api/v1/products/changeProductsStatus", port), ProductsStatusChangeDTO.builder().productsId(ids).productStatus(status).build(), ProductDTO[].class).getBody());
    }
}
