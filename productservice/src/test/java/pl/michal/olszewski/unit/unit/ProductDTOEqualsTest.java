package pl.michal.olszewski.unit.unit;

import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;

import java.util.Arrays;

public class ProductDTOEqualsTest extends LocalEqualsHashCodeTest<ProductDTO> {
    @Override
    protected ProductDTO createInstance() {
        return ProductDTO.builder().productId(1L).productStatus(2L).productDefinition(1L).build();
    }

    @Override
    protected ProductDTO createNotEqualInstance() {
        return ProductDTO.builder().productId(2L).productStatus(3L).productDefinition(1L).build();
    }
}
