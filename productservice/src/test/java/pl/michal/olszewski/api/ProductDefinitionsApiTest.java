package pl.michal.olszewski.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.builders.ProductDefinitionListAssert;
import pl.michal.olszewski.builders.ProductDefinitionsDTOListFactory;
import pl.michal.olszewski.dto.ProductDefinitionDTO;
import pl.michal.olszewski.enums.ProductType;
import pl.michal.olszewski.repository.ProductDefinitionRepository;
import pl.michal.olszewski.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.michal.olszewski.enums.ProductType.PANTS;

public class ProductDefinitionsApiTest extends IntegrationTest {

    @Autowired
    ProductDefinitionRepository productDefinitionRepository;

    @Autowired
    ProductRepository productRepository;

    @Before
    public void setUp() {
        productRepository.deleteAll();
        productDefinitionRepository.deleteAll();
    }

    @Test
    public void should_get_empty_list_of_products() {
        givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(0);

        List<ProductDefinitionDTO> products = thenGetProductDefinitionsFromApi();

        assertThat(products).isEmpty();
    }

    @Test
    public void should_get_3_products() {
        givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(3);

        List<ProductDefinitionDTO> products = thenGetProductDefinitionsFromApi();

        assertThat(products).hasSize(3);
    }

    @Test
    public void should_get_one_product_byName() {
        List<ProductDefinitionDTO> productDefinitionDTOS = givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(1);

        ProductDefinitionDTO product = thenGetOneProductDefinitionFromApiByName(productDefinitionDTOS.get(0).getName());

        assertThat(productDefinitionDTOS.get(0)).isEqualToComparingOnlyGivenFields(product, "name", "description", "imageUrl", "prodType", "price");
    }

    @Test
    public void should_get_one_product() {
        List<ProductDefinitionDTO> productDefinitionDTOS = givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(1);

        ProductDefinitionDTO productDefinitionDTO = thenGetOneProductDefinitionFromApiById(productDefinitionDTOS.get(0).getId());
        assertThat(productDefinitionDTOS.get(0)).isEqualToComparingOnlyGivenFields(productDefinitionDTO, "name", "description", "imageUrl", "prodType", "price");
    }

    @Test
    public void should_get_limit_three_products() {
        givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(6);

        List<ProductDefinitionDTO> productDefinitionDTOS = thenGetNumberProductDefinitionsFromApi(3);

        ProductDefinitionListAssert.assertThat(productDefinitionDTOS)
                .isSuccessful()
                .hasNumberOfItems(3);
    }

    @Test
    public void should_create_a_product() {
        //given
        productDefinitionRepository.deleteAll();
        //when
        thenCreateProductDefinitionByApi("test");

        //then
        assertThat(productDefinitionRepository.findAll().size()).isEqualTo(1);
        assertThat(productDefinitionRepository.findAll().get(0)).isNotNull();
    }

    @Test
    public void should_update_existing_product() {
        //given
        ProductDefinitionDTO productDefinitionDTO = givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(1).get(0);
        productDefinitionDTO.setProdType(PANTS.getValue());

        //when
        thenUpdateProductDefinitionByApi(productDefinitionDTO.getId(), productDefinitionDTO);

        //then
        assertThat(productDefinitionRepository.findByName(productDefinitionDTO.getName()))
                .isNotNull()
                .hasFieldOrPropertyWithValue("prodType", PANTS.getValue());
    }

    @Test
    public void should_delete_existing_product() {
        //given
        ProductDefinitionDTO productDefinitionDTO = givenProductDefinition()
                .buildNumberOfProductDefinitionsAndSave(1).get(0);
        //when
        thenDeleteOneProductDefinitionFromApi(productDefinitionDTO.getId());

        //then
        assertThat(productDefinitionRepository.findOne(productDefinitionDTO.getId())).isNull();
    }

    private ProductDefinitionsDTOListFactory givenProductDefinition() {
        return new ProductDefinitionsDTOListFactory(productDefinitionRepository);
    }

    private List<ProductDefinitionDTO> thenGetProductDefinitionsFromApi() {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/productDefinitions", port), ProductDefinitionDTO[].class).getBody());
    }

    private ProductDefinitionDTO thenGetOneProductDefinitionFromApiById(Long id) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/productDefinitions/%s", port, id), ProductDefinitionDTO.class).getBody();
    }

    private ProductDefinitionDTO thenGetOneProductDefinitionFromApiByName(String name) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/productDefinitions/name/%s", port, name), ProductDefinitionDTO.class).getBody();
    }

    private List<ProductDefinitionDTO> thenGetNumberProductDefinitionsFromApi(int number) {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/productDefinitions?limit=%s", port, number), ProductDefinitionDTO[].class).getBody());
    }

    private void thenCreateProductDefinitionByApi(String name) {
        template.postForEntity(String.format("http://localhost:%s/api/v1/productDefinitions", port), ProductDefinitionDTO.builder().name(name).price(BigDecimal.TEN).build(), ProductDefinitionDTO.class);
    }

    private void thenUpdateProductDefinitionByApi(Long productId, ProductDefinitionDTO productDefinitionDTO) {
        template.put(String.format("http://localhost:%s/api/v1/productDefinitions/%s", port, productId), productDefinitionDTO);
    }

    private void thenDeleteOneProductDefinitionFromApi(Long productId) {
        template.delete(String.format("http://localhost:%s/api/v1/productDefinitions/%s", port, productId));
    }
}
