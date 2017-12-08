package pl.michal.olszewski.unit.unit;

import pl.michal.olszewski.dto.ProductDefinitionDTO;

import java.math.BigDecimal;

public class ProductDefinitionDTOEqualsTest extends LocalEqualsHashCodeTest<ProductDefinitionDTO> {
    @Override
    protected ProductDefinitionDTO createInstance() {
        return ProductDefinitionDTO.builder().name("name").price(BigDecimal.ONE).prodType(1L).description("").build();
    }

    @Override
    protected ProductDefinitionDTO createNotEqualInstance() {
        return ProductDefinitionDTO.builder().name("name2").price(BigDecimal.TEN).prodType(1L).description("").build();
    }
}
