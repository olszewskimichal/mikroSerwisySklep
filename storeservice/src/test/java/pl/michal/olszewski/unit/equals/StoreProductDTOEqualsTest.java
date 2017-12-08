package pl.michal.olszewski.unit.equals;

import pl.michal.olszewski.dto.StoreProductDTO;

import java.util.Arrays;

public class StoreProductDTOEqualsTest extends LocalEqualsHashCodeTest<StoreProductDTO> {
    @Override
    protected StoreProductDTO createInstance() {
        return StoreProductDTO.builder().productsIds(Arrays.asList(1L,2L)).build();
    }

    @Override
    protected StoreProductDTO createNotEqualInstance() {
        return StoreProductDTO.builder().productsIds(Arrays.asList(3L,2L)).build();
    }
}
