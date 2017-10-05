package pl.michal.olszewski.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.JpaTestBase;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductType;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDefinitionRepositoryTest extends JpaTestBase {

    @Autowired
    private ProductDefinitionRepository productDefinitionRepository;

    @Test
    public void shouldFindProductDefinitionByName() {
        //given
        this.entityManager.persistAndFlush(ProductDefinition.builder().name("nazwa").prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP)).build());
        //when
        ProductDefinition byName = this.productDefinitionRepository.findByName("nazwa");
        //then
        assertThat(byName).isNotNull();
        assertThat(byName.getName()).isEqualTo("nazwa");
        assertThat(byName.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
    }

    @Test
    public void shouldUpdateProductDefinition() {
        //given
        ProductDefinition productDefinition = this.entityManager.persistAndFlush(ProductDefinition.builder().name("nazwaTestowa").prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP)).build());
        //when
        this.productDefinitionRepository.updateProductDefinition(productDefinition.getName(), productDefinition.getDescription(), productDefinition.getImageUrl(), ProductType.PANTS.getValue(), BigDecimal.ONE, productDefinition.getId());
        //then
        entityManager.clear();
        ProductDefinition updatedProductDef = productDefinitionRepository.findOne(productDefinition.getId());
        assertThat(updatedProductDef).isNotNull();
        assertThat(updatedProductDef.getName()).isEqualTo("nazwaTestowa");
        assertThat(updatedProductDef.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.ONE.stripTrailingZeros());
        assertThat(updatedProductDef.getProdType()).isEqualTo(ProductType.PANTS.getValue());
    }

}