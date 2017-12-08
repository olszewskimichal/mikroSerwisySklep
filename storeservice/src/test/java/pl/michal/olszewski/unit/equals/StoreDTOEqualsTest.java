package pl.michal.olszewski.unit.equals;

import pl.michal.olszewski.dto.StoreDTO;

public class StoreDTOEqualsTest extends LocalEqualsHashCodeTest<StoreDTO> {
    @Override
    protected StoreDTO createInstance() {
        return StoreDTO.builder().name("name").city("city").country("PL").zipCode("zip").build();
    }

    @Override
    protected StoreDTO createNotEqualInstance() {
        return StoreDTO.builder().name("name2").city("city2").country("PL").zipCode("zip").build();
    }
}
