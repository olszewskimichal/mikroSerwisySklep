package pl.michal.olszewski.unit.unit;

import pl.michal.olszewski.dto.ProductsStatusChangeDTO;

import java.util.Arrays;

public class ProductStatusChangeDTOEqualsTest extends LocalEqualsHashCodeTest<ProductsStatusChangeDTO> {
    @Override
    protected ProductsStatusChangeDTO createInstance() {
        return ProductsStatusChangeDTO.builder().productsId(Arrays.asList(1L,2L)).productStatus(2L).build();
    }

    @Override
    protected ProductsStatusChangeDTO createNotEqualInstance() {
        return ProductsStatusChangeDTO.builder().productStatus(1L).productsId(Arrays.asList(3L,2L)).build();
    }
}
