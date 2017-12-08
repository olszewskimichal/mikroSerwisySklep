package pl.michal.olszewski.unit.equals;

import pl.michal.olszewski.dto.WarehouseDTO;

public class WarehouseDTOEqualsTest extends LocalEqualsHashCodeTest<WarehouseDTO> {
    @Override
    protected WarehouseDTO createInstance() {
        return WarehouseDTO.builder().name("name").city("city").country("PL").zipCode("zip").build();
    }

    @Override
    protected WarehouseDTO createNotEqualInstance() {
        return WarehouseDTO.builder().name("name2").city("city2").country("PL").zipCode("zip").build();
    }
}
