package pl.michal.olszewski.unit.equals;

import pl.michal.olszewski.dto.WarehouseProductDTO;

import java.util.Arrays;

public class WarehouseProductDTOEqualsTest extends LocalEqualsHashCodeTest<WarehouseProductDTO> {
    @Override
    protected WarehouseProductDTO createInstance() {
        return WarehouseProductDTO.builder().warehouseId(1L).productsIds(Arrays.asList(1L, 2L)).build();
    }

    @Override
    protected WarehouseProductDTO createNotEqualInstance() {
        return WarehouseProductDTO.builder().warehouseId(2L).productsIds(Arrays.asList(3L, 2L)).build();
    }
}
